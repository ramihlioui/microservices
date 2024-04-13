import { Component } from '@angular/core';
import {Reservation} from "../../../Model/reservation";
import {ReservationService} from "../../../services/servicesReservation/reservation.service";
import {ReservationAdmin} from "../../../Model/ReservationAdmin";
import * as FileSaver from 'file-saver';


@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent {
  reservations: ReservationAdmin[] = [];

  constructor(private reservationService: ReservationService) { }


  ngOnInit() {

    this.getAllReservations()
    console.log(this.reservations)

  }

  getAllReservations() {
    this.reservationService.getAllReservations()
      .subscribe(
        (data: ReservationAdmin[]) => {
          this.reservations = data;
          console.log(this.reservations)
        },
        (error) => {
          console.error('Error fetching reservations:', error);
        }
      );
  }


  exportExcel() {
    import("xlsx").then(xlsx => {
      const worksheet = xlsx.utils.json_to_sheet(this.reservations); // university

      const workbook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
      const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
      this.saveAsExcelFile(excelBuffer, "sales");
    });
  }
  saveAsExcelFile(buffer: any, fileName: string): void {
    let EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
    let EXCEL_EXTENSION = ".xlsx";
    const blob = new Blob([buffer], { type: EXCEL_TYPE });
    FileSaver.saveAs(blob, fileName + EXCEL_EXTENSION);
  }

}
