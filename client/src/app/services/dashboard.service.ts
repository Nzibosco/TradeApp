import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { IOrder } from '../shared/models/order';
import { IPrices } from '../shared/models/prices';
import { IBalance } from '../shared/models/balance';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  /**
   * Get asset balances in quantity 
   * @returns 
   */
  getBalances(){
    return this.http.get<IBalance[]>(`${environment.url}/orders/balances`); 
  }

  /**
   * Get prices in USD for allowed assets
   * @returns 
   */
  getPrices(){
    return this.http.get<IPrices[]>(`${environment.url}/orders/prices`);
  }

  /**
   * Market order (BUY or SELL)
   */
  marketOrder(order: IOrder){
    return this.http.post(`${environment.url}/orders/market`, order)
  }

}
