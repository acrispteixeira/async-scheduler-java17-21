import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { JobsComponent } from './jobs/jobs.component';
import { HttpClientModule } from '@angular/common/http';
import { JobsService } from './jobs/jobs.service';

@NgModule({
  declarations: [
    AppComponent,
    JobsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [JobsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
