import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Reservation } from 'src/app/Model/reservation';
import { environment } from 'src/environments/environment.development';
import {ReservationAdmin} from "../../Model/ReservationAdmin";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {


  constructor(private http: HttpClient) { }

  ajouterReservation(data:any) {
    return this.http.post(`${environment.BaseUrl}/reservation/reservations`, data);
  }

  updateReservation(reservation: Reservation) {
    return this.http.put(`${environment.BaseUrl}/reservation/update`, reservation);
  }

  getAllReservations() {
    return this.http.get<ReservationAdmin[]>(`${environment.BaseUrl}/reservation/all`);
  }

  getReservationById(idReservation: number) {
    return this.http.get<Reservation>(`${environment.BaseUrl}/reservation/${idReservation}`);
  }

  getCurrentReservationByEtudiantId(idEtudiant: number) {
    return this.http.get<Reservation>(`${environment.BaseUrl}/reservation/student/${idEtudiant}`);
  }

  annulerReservation(cin: number) {
    return this.http.put(`${environment.BaseUrl}/reservation/annulerReservation/${cin}`, null);
  }
}
