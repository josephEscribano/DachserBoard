import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

export const jsonInterceptor = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  if (!req.headers.has('Content-Type')) {
    req = req.clone({
      headers: req.headers.set('Content-Type', 'application/json'),
    });
  }
  return next(req);
};
