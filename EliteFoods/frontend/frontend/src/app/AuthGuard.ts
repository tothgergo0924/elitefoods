import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';

import { Router } from '@angular/router';
import { AuthServiceService } from './auth-service.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthServiceService);
  const router = inject(Router);
  
  if (authService.isLoggedIn()) {
    return true;
  } else {
    return router.navigate(['/home']);
  }
};