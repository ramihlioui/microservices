const mongoose = require('mongoose');
const express = require('express');
const Eureka = require('eureka-js-client').Eureka;
const axios = require('axios');

const bodyParser = require('body-parser');
require('dotenv').config(); 

const app = express();
const port = 3000;



const client = new Eureka({
  instance: {
    app: 'reservation',
    hostName: 'localhost',
    ipAddr: '127.0.0.1',
    port: {
      '$': 8080 ,
      '@enabled': 'true',
    },
    vipAddress: 'reservation',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    host: 'localhost', 
    port: 8761, 
    servicePath: '/eureka',
  },
  registerWithEureka: true
});

client.start();


mongoose.connect(process.env.MONGODB_URL, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  });
  
  app.use(bodyParser.json());

// Reservation schema
const reservationSchema = new mongoose.Schema({
  reservationDate: {
    type: Date,
    required: true,
    default: Date.now
  },
  etudiantId: {
    type: [Number], 
    required: true,
  },
  chamberId: {
    type: Number,
    required: true,
  },
});

const Reservation = mongoose.model('Reservation', reservationSchema);

app.post('/reservation/reservations', async (req, res) => {
  try {
    const { chamberId, etudiantId } = req.body;

    const existingReservation = await Reservation.findOne({ chamberId });

    if (existingReservation) {

      if (existingReservation.etudiantId.includes(etudiantId)) {
        return res.status(400).json({ error: 'Student ID already exists in the reservation' });
      }

      existingReservation.etudiantId.push(etudiantId);
      await existingReservation.save();

      res.status(200).json(existingReservation);

    } else {

      const newReservation = new Reservation({ chamberId, etudiantId });
      await newReservation.save();

      res.status(201).json(newReservation);

    }
  } catch (error) {
    res.status(400).json({ error: error.message });
  }

});


// Endpoint to fetch all bloc and chambre IDs
app.get('/reservation/blocAndChambreIds', async (req, res) => {
  try {
    // Make parallel requests to fetch bloc and chambre IDs
    const [blocResponse, chambreResponse] = await Promise.all([
      axios.get('http://localhost:8090/bloc/AllBlocs'),
      axios.get('http://localhost:8090/chambre/afficherchambres')
    ]);

    // Extract bloc and chambre IDs from responses
    const blocIds = blocResponse.data;
    const chambreIds = chambreResponse.data;

    // Construct the response object with both IDs
    const responseObj = {
      blocIds: blocIds,
      chambreIds: chambreIds
    };

    res.status(200).json(responseObj);
  } catch (error) {
    res.status(500).json({ error: 'Internal Server Error' });
  }
});  




  app.get('/reservation/reservations/:id', async (req, res) => {
    try {
      const reservation = await Reservation.findById(req.params.id);
      if (!reservation) {
        return res.status(404).json({ error: 'Reservation not found' });
      }
      res.json(reservation);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  });


  app.get('/reservation/reservations/chamber/:chamberId', async (req, res) => {
    try {
      const reservations = await Reservation.find({ chamberId: req.params.chamberId });
      res.json(reservations);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  });
  app.put('/reservation/reservations/:id', async (req, res) => {
    try {
      const reservation = await Reservation.findByIdAndUpdate(req.params.id, req.body, {
        new: true,
        runValidators: true,
      });
      if (!reservation) {
        return res.status(404).json({ error: 'Reservation not found' });
      }
      res.json(reservation);
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
  });
  
  app.delete('/reservation/reservations/:id', async (req, res) => {
    try {
      const reservation = await Reservation.findByIdAndDelete(req.params.id);
      if (!reservation) {
        return res.status(404).json({ error: 'Reservation not found' });
      }
      res.json({ message: 'Reservation deleted successfully' });
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  });
  
  app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
  });
  
  module.exports = Reservation;
