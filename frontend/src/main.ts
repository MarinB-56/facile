import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { LOCALE_ID, ApplicationConfig } from '@angular/core';

registerLocaleData(localeFr);

const localeConfig: ApplicationConfig = {
  providers: [
    ...appConfig.providers,
    { provide: LOCALE_ID, useValue: 'fr-FR' }
  ]
};

bootstrapApplication(AppComponent, localeConfig)
  .catch((err) => console.error(err));
