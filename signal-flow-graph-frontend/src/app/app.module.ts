import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { LineComponent } from './line/line.component';
import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { LoopComponent } from './loop/loop.component';
import { CurvedLineComponent } from './curved-line/curved-line.component';
import { ResultComponent } from './result/result.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [
    LineComponent,
    AppComponent,
    LoopComponent,
    CurvedLineComponent,
    ResultComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
