/*******************************************************************************
 * Project: Droplet - Toolkit for Liquid Art Photographers
 * Copyright (C) 2012 Stefan Brenner
 *
 * This file is part of Droplet.
 *
 * Droplet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Droplet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Droplet. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

#ifndef __DROPLET_H__
#define __DROPLET_H__


#define DEVICE_NUMBERS  70


// TODO use bit masks for better performance
struct Action {
  unsigned short offset;    // ms                        // 2 bytes
  unsigned char mode;       // pin mode (HIGH/LOW)       // 1 byte
  unsigned char pin;        // pin # of device           // 1 byte
  Action *next;             // pointer to next action    // 4 bytes
};                                                       // 8 bytes

struct Droplet {
  Action *actions;  // pointer to first action           // 4 bytes
};


extern Droplet droplet;
extern char deviceMapping[];


void addActions(int deviceNumber, int offset, int duration);
void addAction(Action *action);
void executeActions();
void clearActions();
void printActions();


#endif
