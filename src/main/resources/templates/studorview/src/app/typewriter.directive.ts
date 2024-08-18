import { Directive, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[appTypewriter]'
})
export class TypewriterDirective implements OnInit{
  @Input('appTypewriter') text!: string;
  @Input() speed: number = 50;

  constructor(private el: ElementRef) { }

  ngOnInit(): void {
      this.typeWriterEffect(this.text, this.speed);
  }

  private typeWriterEffect(text: string, speed: number){
    let index = 0;
    const intervalId = setInterval(() => {
      if (index < text.length) {
        this.el.nativeElement.innerHTML += text.charAt(index);
        index++;
      } else {
        clearInterval(intervalId);
      }
    }, speed);
  }
}
