import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { MenuItem } from 'primeng/api';
import { Observable } from 'rxjs';
import { DashboardService } from 'src/app/services/dashboard.service';
import { IBalance } from 'src/app/shared/models/balance';
import { ICrypto } from 'src/app/shared/models/crypto';
import { IPrices } from 'src/app/shared/models/prices';
import { getBalance, latestPrices } from './state/dashboard.actions';
import { DashboardState } from './state/dashboard.reducer';
import { selectLatestPrices } from './state/dashboard.selector';
import { FacebookService, InitParams } from 'ngx-facebook';
  

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  items: MenuItem[] = [];
  order: string = '';
  cryptos: ICrypto[] = [];
  //balance: Observable<string>;
  balance: string = '$00.00'; 
  balances: IBalance[];
  prices: IPrices[];

  constructor(private store: Store, private dashService: DashboardService, private facebookService: FacebookService) {
    //Getting lates prices 
    this.dashService.getPrices().subscribe(
      {
        next: (price) => this.prices = price,
        error: (err) => console.log(err), 
        complete: () => console.log('Done getting prices from backend')
      }
    )
  }

  ngOnInit(): void {

    this.dashService.getBalances().subscribe(
      {
        next: (bal) => {
          this.balances = bal;
          this.getCryptoBalances();
          this.aggregateBalance();
        },
        error: (err) => console.log(err),
        complete: () => console.log('API call for Balances done!')
      }
    )

    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-fw pi-home',
        routerLink: ['']
      },
      {
        label: 'Profile',
        icon: 'pi pi-fw pi-user',
        command: (e) => {
          // alert(e.item.label);
          // console.log(e.item);
        }
      },
      {
        label: 'Events',
        icon: 'pi pi-fw pi-calendar',
        items: [
          {
            label: 'Edit',
            icon: 'pi pi-fw pi-pencil',
            items: [
              {
                label: 'Save',
                icon: 'pi pi-fw pi-calendar-plus'
              },
              {
                label: 'Delete',
                icon: 'pi pi-fw pi-calendar-minus'
              },

            ]
          },
          {
            label: 'Archieve',
            icon: 'pi pi-fw pi-calendar-times',
            items: [
              {
                label: 'Remove',
                icon: 'pi pi-fw pi-calendar-minus'
              }
            ]
          }
        ]
      },
      {
        label: 'Dashboard',
        icon: 'pi pi-fw pi-unlock',
        routerLink: ['/dashboard']
      },
      {
        label: 'Logout',
        icon: 'pi pi-fw pi-power-off'
      }
    ]

  }

  //Click on menu items
alertPrices(){
this.store.select(selectLatestPrices)
}

getCryptoBalances () {

  //Make an array of cryptos and their balances 
  this.cryptos = this.balances.map(bal => {
    console.log(bal)
    return {
      symbol: bal.asset, 
      balance: (bal.free).substring(0, (bal.free).indexOf('.') + 3), 
      price: (this.prices.find(p => p.symbol === bal.asset.concat('USD')))?.price || '1.00'
    }
  })
}

aggregateBalance(){
  console.log(this.prices);
  console.log(this.balances);
  console.log(this.cryptos);
  const initVal = 0;
  this.balance = this.cryptos.reduce((prev, curr) => prev + (parseFloat(curr.balance) * parseFloat(curr.price)), initVal).toFixed(2).toString();
}

// private initFacebookService(): void {
//   const initParams: InitParams = { xfbml:true, version:'v3.2'};
//   this.facebookService.init(initParams);
// }


}
