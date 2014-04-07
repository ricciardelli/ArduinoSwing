int led_4 = 4;
int led_5 = 5;
int led_6 = 6;
int led_7 = 7;
int led_8 = 8;

void setup() {
  Serial.begin(9600);
  pinMode(led_4, OUTPUT);
  pinMode(led_5, OUTPUT);
  pinMode(led_6, OUTPUT);
  pinMode(led_7, OUTPUT);
  pinMode(led_8, OUTPUT);
  Serial.println("Enter LED number: ");
}

void loop() {
  if (Serial.available() > 0) {
    
    int led = ((int) Serial.read()) - 48;
    
    switch(led) {
      case 0:
        turn_off();
        return;
      break;
      case 1:
        turn_on();
        return;
      break;
    }
    
    if (led < led_4 || led > led_8) {
      Serial.println("Error");
      return;
    }
    
    Serial.println("Led signal: " + String(led));
    
    switch_led(led);
  }
  
}

// Switches a LED depending on its current state.
void switch_led(int led) {
  if (digitalRead(led) == LOW) {
    Serial.println("Turning on LED " + String(led));
    digitalWrite(led, HIGH);
  } else {
    Serial.println("Turning off LED " + String(led));
    digitalWrite(led, LOW);
  }
}

// Turn off all LEDs 
void turn_off() {
  for (int i = led_4; i <= led_8; i++) {
    digitalWrite(i, LOW);
  }
  Serial.println("Turning off all LEDs");
}

// Turn on all LEDs
void turn_on() {
  for (int i = led_4; i <= led_8; i++) {
    digitalWrite(i, HIGH);
  }
  Serial.println("Turning on all LEDs");
}
 
