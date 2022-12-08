import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  tokenDiv: boolean = true;
  token: string = 'dsgs';
  tokenReady:boolean = false;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  unhide(){
    this.tokenDiv = false;
  }

  setToken(token:string){
    this.token = token;
    console.log(this.token);
    if(token.length > 4){
      this.router.navigateByUrl('/dashboard');
    }
  }

}
