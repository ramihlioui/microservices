const mongoose = require('mongoose');
const express = require('express');
const Eureka = require('eureka-js-client').Eureka;
const axios = require('axios');

const bodyParser = require('body-parser');
require('dotenv').config();

const app = express();
const port = 3000;

/*
registerWithEureka("reservation","localhost", "3000")


async function registerWithEureka(serviceName, ipAddress, port) {
  const eurekaUrl = 'http://localhost:8761/eureka/apps/' + serviceName;

  const instance = {
    hostName: serviceName,
    ipAddress: ipAddress,
    port: port,
    instanceId: serviceName + ':' + ipAddress + ':' + port,
    app: serviceName,
    vipAddress: serviceName,
    secureVipAddress: serviceName,
    status: 'UP',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn'
    }
  };

  try {
    const response = await axios.post(eurekaUrl, { instance }, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log('Registered with Eureka:', response.status);
  } catch (error) {
    console.error('Error registering with Eureka:', error.response?.data || error.message);
  }
}
*/
const client = new Eureka({
  instance: {
    app: 'reservation',
    hostName: 'localhost',
  //  ipAddr: '127.0.0.1',
    port: 3000,
    vipAddress: 'reservation',
    dataCenterInfo: {
      name: 'MyOwn',
    },
  },
  eureka: {
    host: 'localhost',
    port: 8761,
    servicePath: '/eureka/apps',
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



app.get('/reservation/student/:id', async (req, res) => {
  try {
    const reservation = await Reservation.find({etudiantId: req.params.id});
    if (!reservation) {
      return res.status(500).json({ error: 'Reservation not found' });
    }
    res.json(reservation);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});


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

app.get('/reservation/all', async (req, res) => {
  try {
    const reservations = await Reservation.find();

    if (!reservations) {
      console.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
      return res.status(404).json({ error: 'Reservation not found' });
    }

    const modifiedReservations = [];

    for ( const res of reservations){
     const etudiant = await axios.get(`http://localhost:8090/etudiant/${res.etudiantId}`);
     const etures = etudiant.data;
      console.log(etures)

     const chambre = await axios.get(`http://localhost:8090/chambre/afficherchambre/${res.chamberId}`);
     const chres =  chambre.data;
      console.log(chres)


      const modifiedReservation = {
        _id: res._id,
        reservationDate: res.reservationDate,
        etudiantId: res.etudiantId,
        etudiantName: etures.nom,
        chambreNumber: chres.numeroChambre
      };

      modifiedReservations.push(modifiedReservation);
    }


    res.json(modifiedReservations);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});



app.get('/reservation/reservations/:id', async (req, res) => {
  try {
    const reservation = await Reservation.findBy(req.params.id);
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
