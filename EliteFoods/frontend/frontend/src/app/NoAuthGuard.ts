
import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';

import { Router } from '@angular/router';
import { AuthServiceService } from './auth-service.service';

export const NoAuthGuard: CanActivateFn = () => {
  const authService = inject(AuthServiceService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return router.navigate(['/home']);
  } else {
    return true;
  }
};