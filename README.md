# York-Pirates

## File Structure

The project is organised in the way described by the libGDX documentation in order to assure that it can be easily compiled using Gradle. The structure is thus:
- core - This contains the main game code. All the main assets and code that are not specific to any display or device are stored here.
- desktop - This contains the code specifically for the desktop implementation (which is the only type of device we are currently compiling for). The main thing that is in this folder is the code for the game window itself.
- gradle - This contains information required by gradle to compile the code.

## Core Class Structure
- YorkPirates - Main game Class
- College - A class that inherits graphics.g2d.Sprite with College specific code
- Boat -  A class that inherits graphics.g2d.Sprite with boat specific code
- Projectile - A class that inherits graphics.g2d.Sprite with weapon specific code

## Building
Set directory to the York-Pirates root directory and run:

`gradle run`

or

`gradle build`
