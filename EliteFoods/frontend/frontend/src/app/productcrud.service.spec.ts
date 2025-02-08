import { TestBed } from '@angular/core/testing';

import { ProductcrudService } from './productcrud.service';

describe('ProductcrudService', () => {
  let service: ProductcrudService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductcrudService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
