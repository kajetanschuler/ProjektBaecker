/**************************************************************************/
/*!
    @file     readMifareClassic.pde
    @author   Adafruit Industries
	@license  BSD (see license.txt)

    This example will wait for any ISO14443A card or tag, and
    depending on the size of the UID will attempt to read from it.

    If the card has a 4-byte UID it is probably a Mifare
    Classic card, and the following steps are taken:

    Reads the 4 byte (32 bit) ID of a MiFare Classic card.
    Since the classic cards have only 32 bit identifiers you can stick
	them in a single variable and use that to compare card ID's as a
	number. This doesn't work for ultralight cards that have longer 7
	byte IDs!

    Note that you need the baud rate to be 115200 because we need to
	print out the data and read from the card at the same time!

  This is an example sketch for the Adafruit PN532 NFC/RFID breakout boards
  This library works with the Adafruit NFC breakout
  ----> https://www.adafruit.com/products/364

  Check out the links above for our tutorials and wiring diagrams
  These chips use SPI to communicate, 4 required to interface

  Adafruit invests time and resources providing this open source code,
  please support Adafruit and open-source hardware by purchasing
  products from Adafruit!
*/
/**************************************************************************/
#include <Wire.h>
#include <SPI.h>
#include <Adafruit_PN532.h>
#include <LiquidCrystal_I2C.h>

// If using the breakout with SPI, define the pins for SPI communication.
#define PN532_SCK  (2)
#define PN532_MOSI (3)
#define PN532_SS   (4)
#define PN532_MISO (5)

// If using the breakout or shield with I2C, define just the pins connected
// to the IRQ and reset lines.  Use the values below (2, 3) for the shield!
#define PN532_IRQ   (2)
#define PN532_RESET (3)  // Not connected by default on the NFC Shield

// Uncomment just _one_ line below depending on how your breakout or shield
// is connected to the Arduino:

// Use this line for a breakout with a SPI connection:
Adafruit_PN532 nfc(PN532_SCK, PN532_MISO, PN532_MOSI, PN532_SS);

// Use this line for a breakout with a hardware SPI connection.  Note that
// the PN532 SCK, MOSI, and MISO pins need to be connected to the Arduino's
// hardware SPI SCK, MOSI, and MISO pins.  On an Arduino Uno these are
// SCK = 13, MOSI = 11, MISO = 12.  The SS line can be any digital IO pin.
//Adafruit_PN532 nfc(PN532_SS);

// Or use this line for a breakout or shield with an I2C connection:
//Adafruit_PN532 nfc(PN532_IRQ, PN532_RESET);

#if defined(ARDUINO_ARCH_SAMD)
// for Zero, output on USB Serial console, remove line below if using programming port to program the Zero!
// also change #define in Adafruit_PN532.cpp library file
#define Serial SerialUSB
#endif

// LiquidCrystal_I2C lcd(0x27, 16, 2); // Setup of LCD Display, 0x27 is the I2C address of Display Module

void setup(void) {
#ifndef ESP8266
  while (!Serial); // for Leonardo/Micro/Zero
#endif

  //lcd.init();
  // lcd.backlight();
  // lcd.clear();
  // lcd.setCursor(0,0);

  Serial.begin(115200);
  //Serial.println("Hello!");



  nfc.begin();

  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {
    Serial.print("Didn't find PN53x board");
    while (1); // halt
  }
  // Got ok data, print it out!
  //Serial.print("Found chip PN5"); Serial.println((versiondata>>24) & 0xFF, HEX);
  //Serial.print("Firmware ver. "); Serial.print((versiondata>>16) & 0xFF, DEC);
  //Serial.print('.'); Serial.println((versiondata>>8) & 0xFF, DEC);

  // configure board to read RFID tags
  nfc.SAMConfig();

  //Serial.println("Waiting for an ISO14443A Card ...");
  //lcd.print("Place card...");
}


void loop(void) {
  uint8_t success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Buffer to store the returned UID
  uint8_t uidLength;                        // Length of the UID (4 or 7 bytes depending on ISO14443A card type)

  // Wait for an ISO14443A type cards (Mifare, etc.).  When one is found
  // 'uid' will be populated with the UID, and uidLength will indicate
  // if the uid is 4 bytes (Mifare Classic) or 7 bytes (Mifare Ultralight)
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

  if (success) {
    // Display some basic information about the card
    // Serial.println("Found an ISO14443A card");
    // Serial.print("  UID Length: ");Serial.print(uidLength, DEC);Serial.println(" bytes");
    // Serial.print("  UID Value: ");
    // nfc.PrintHex(uid, uidLength);
    // Serial.println("");

    if (uidLength == 4)
    {
      // We probably have a Mifare Classic card ...
      //Serial.println("Seems to be a Mifare Classic card (4 byte UID)");

      uint32_t cardid = uid[0];
      cardid <<= 8;
      cardid |= uid[1];
      cardid <<= 8;
      cardid |= uid[2];
      cardid <<= 8;
      cardid |= uid[3];
      //     lcd.clear();
      //      lcd.setCursor(0, 0);
      //     lcd.print("Card read");
      //    lcd.setCursor(0,1);
      //    lcd.print("UID: ");
      //    lcd.print(cardid, DEC);
      //Serial.print("Card ID: ");
      //Serial.println(cardid, DEC);

      // Now we need to try to authenticate it for read/write access
      // Try with the factory default KeyA: 0xFF 0xFF 0xFF 0xFF 0xFF 0xFF
      //Serial.println("Trying to authenticate block 4 with default KEYA value");
      uint8_t keya[6] = { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };

      // Start with block 4 (the first block of sector 1) since sector 0
      // contains the manufacturer data and it's probably better just
      // to leave it alone unless you know what you're doing
      success = nfc.mifareclassic_AuthenticateBlock(uid, uidLength, 4, 0, keya);

      if (success)
      {
        //Serial.println("Sector 1 (Blocks 4..7) has been authenticated");
        uint8_t data[16];

        // If you want to write something to block 4 to test with, uncomment
        // the following line and this text should be read back in a minute
        //        uint8_t filiale[] = {'F', 'i', 'l', ':', '1', '2', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        //        uint8_t artikel[] = {'A', 'r', 't', ':', 'B', 'r', 'e', 'z', 'e', 'l', 0, 0, 0, 0, 0, 0};
        //
        //        success = nfc.mifareclassic_WriteDataBlock (4, filiale);
        //
        //        if (success)
        //        {
        //          Serial.println("Write successful");
        //        }
        //
        //        success = nfc.mifareclassic_WriteDataBlock (5, artikel);
        //
        //        if (success)
        //        {
        //          Serial.println("Write successful");
        //        }

        // Try to read the contents of block 4
        success = nfc.mifareclassic_ReadDataBlock(4, data);

        if (success)
        {
          // Data seems to have been read ... spit it out
          //Serial.println("Reading Block 4:");
          //       lcd.setCursor(0, 0);
          //       lcd.print("Card read");
          nfc.PrintHexChar(data, 16);

          // Wait a bit before reading the card again
          delay(200);
        }
        else
        {
          Serial.println("Ooops ... unable to read the requested block.  Try another key?");
        }

        success = nfc.mifareclassic_ReadDataBlock(5, data);

        if (success)
        {
          // Data seems to have been read ... spit it out
          //Serial.println("Reading Block 5:");
          //       lcd.setCursor(0, 0);
          //         lcd.print("Card read");
          nfc.PrintHexChar(data, 16);

          // Wait a bit before reading the card again
          delay(200);
        }
        else
        {
          Serial.println("Ooops ... unable to read the requested block.  Try another key?");
        }

      }
      else
      {
        Serial.println("Ooops ... authentication failed: Try another key?");
      }
    }

    delay(2000);

    if (uidLength == 7)
    {
      // We probably have a Mifare Ultralight card ...
      Serial.println("Seems to be a Mifare Ultralight tag (7 byte UID)");

      // Try to read the first general-purpose user page (#4)
      Serial.println("Reading page 4");
      uint8_t data[32];
      success = nfc.mifareultralight_ReadPage (4, data);
      if (success)
      {
        // Data seems to have been read ... spit it out
        nfc.PrintHexChar(data, 4);
        Serial.println("");

        // Wait a bit before reading the card again
        delay(1000);
      }
      else
      {
        Serial.println("Ooops ... unable to read the requested page!?");
      }
    }
  }
}
