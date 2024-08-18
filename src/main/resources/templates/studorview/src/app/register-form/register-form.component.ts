import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import {
  trigger,
  style,
  animate,
  transition,
  query,
  group,
} from '@angular/animations';

function dateRangeValidator(minDate: string, maxDate: string) {
  return (control: AbstractControl): ValidationErrors | null => {
    const inputDate = new Date(control.value);
    const min = new Date(minDate);
    const max = new Date(maxDate);

    if (isNaN(inputDate.getTime())) {
      return { invalidDate: true };
    }

    if (inputDate < min || inputDate > max) {
      return { dateOutOfRange: true };
    }

    return null;
  };
}

function passwordMatchValidator(
  control: AbstractControl
): ValidationErrors | null {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  if (password?.pristine || confirmPassword?.pristine) {
    return null;
  }

  if (password?.value === confirmPassword?.value) {
    return null;
  }

  return { passwordMismatch: true };
}

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
  animations: [
    trigger('slideAnimation', [
      transition(':increment', [
        style({ position: 'relative' }),
        query(':enter, :leave', [
          style({
            position: 'absolute',
            top: 0,
            width: '100%',
            opacity: 1,
          }),
        ]),
        group([
          query(':leave', [
            animate(
              '500ms ease',
              style({ transform: 'translateX(-100%)', opacity: 0 })
            ),
          ]),
          query(':enter', [
            style({ transform: 'translateX(100%)', opacity: 0 }),
            animate(
              '500ms ease',
              style({ transform: 'translateX(0)', opacity: 1 })
            ),
          ]),
        ]),
      ]),
      transition(':decrement', [
        style({ position: 'relative' }),
        query(':enter, :leave', [
          style({
            position: 'absolute',
            top: 0,
            width: '100%',
            opacity: 1,
          }),
        ]),
        group([
          query(':leave', [
            animate(
              '500ms ease',
              style({ transform: 'translateX(100%)', opacity: 0 })
            ),
          ]),
          query(':enter', [
            style({ transform: 'translateX(-100%)', opacity: 0 }),
            animate(
              '500ms ease',
              style({ transform: 'translateX(0)', opacity: 1 })
            ),
          ]),
        ]),
      ]),
    ]),
  ],
})
export class RegisterFormComponent {
  registerForm: FormGroup;
  currentStep: number = 1;
  showPassword: boolean = false;
  passwordStrength: number = 0;
  passwordColor: string = 'bg-danger';
  emailDomain: string = '@studor.com';

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      lastName: ['', [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      birthDate: [
        '',
        [Validators.required, dateRangeValidator('1935-01-01', '2010-12-31')],
      ],
      gender: ['', Validators.required],
      username: ['', Validators.required],
      emailBase: [
        '',
        [Validators.required, Validators.pattern('[a-z0-9._%+-]+')],
      ],
      passwordGroup: this.fb.group(
        {
          password: ['', [Validators.required, Validators.minLength(8)]],
          confirmPassword: ['', Validators.required],
        },
        { validators: passwordMatchValidator }
      ),
    });
  }

  onPasswordInput(){
    const password =  this.registerForm.get('passwordGroup.password')?.value || '';
    const strength = this.calculatePasswordStrength(password);

    this.passwordStrength = strength;
    this.passwordColor = this.updatePasswordStrengthColor(strength);
  }

  calculatePasswordStrength(password: string): number {
    let strength: number = 0;
    if (password.length >= 8) strength += 25
    if (/[A-Z]/.test(password)) strength += 25
    if (/[0-9]/.test(password)) strength += 25
    if (/[\W]/.test(password)) strength += 25

    return strength;
  }

  updatePasswordStrengthColor(strength: number){
    if (strength <= 25) {
      return 'bg-danger';
    } else if (strength > 25 && strength <= 75) {
      return 'bg-warning';
    } else {
      return'bg-success';
    }
  }

  nextStep() {
    if (this.currentStep === 1) {
      if (this.isValidStepOne()) this.currentStep = 2;
      else {
        this.registerForm.get('firstName')?.markAsTouched();
        this.registerForm.get('lastName')?.markAsTouched();
        this.registerForm.get('birthDate')?.markAsTouched();
        this.registerForm.get('gender')?.markAsTouched();
      }
    }
  }

  prevStep() {
    this.currentStep = 1;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  isValidStepOne(): boolean {
    if (
      this.registerForm.get('firstName')?.valid &&
      this.registerForm.get('lastName')?.valid &&
      this.registerForm.get('birthDate')?.valid &&
      this.registerForm.get('gender')?.valid
    ) {
      return true;
    }
    return false;
  }

  isValidStepTwo(): boolean {
    if (
      this.registerForm.get('username')?.valid &&
      this.registerForm.get('emailBase')?.valid &&
      this.registerForm.get('passwordGroup.password')?.valid &&
      this.registerForm.get('passwordGroup.confirmPassword')?.valid
    ) {
      return true;
    }
    return false;
  }

  onSubmit() {
    if (!this.isValidStepTwo()) {
      this.registerForm.get('username')?.markAllAsTouched();
      this.registerForm.get('emailBase')?.markAllAsTouched();
      this.registerForm.get('passwordGroup.password')?.markAllAsTouched();
      this.registerForm
        .get('passwordGroup.confirmPassword')
        ?.markAllAsTouched();
    }
  }
}
