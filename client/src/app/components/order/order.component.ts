import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DashboardService } from 'src/app/services/dashboard.service';
import { IAsset } from 'src/app/shared/models/asset';
import { IOrderType } from 'src/app/shared/models/orderType';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  orderForm: FormGroup; 
  assets: IAsset[];
  types: IOrderType[];
  orderError: string;
  orderSuccess: string;

  constructor(private fb: FormBuilder, private orderService: DashboardService) {
    this.assets = [
      {name: 'Ethereum', symbol: 'ETH'},
      {name: 'Cadano', symbol: 'ADA'},
      {name: 'Dogecoin', symbol: 'DOGE'},
      {name: 'Binance Coin', symbol: 'BNB'},
    ]; 

    this.types = [
      {type: 'BUY', code: 'BUY'}, 
      {type: 'SELL', code: 'SELL'}
    ]
   }

  ngOnInit(): void {
    this.orderForm = this.fb.group({
      asset: ['', [Validators.required]], 
      quantity: [0.00, [Validators.min(0.01), Validators.required]],
      orderType: ['', [Validators.required]]
    })
  }

  placeAnOrder(){
    console.log(this.orderForm.value);
    this.orderService.marketOrder(this.orderForm.value).subscribe({
      next: () => console.log('SUCCESS'), 
      error: (err) => this.orderError = err['message'],
      complete: () => this.orderSuccess = 'Your order has been successfully placed'
    });

    this.orderForm.setValue({asset: '', quantity: 0.00, orderType: ''}) //Clearing the form
  }

}
