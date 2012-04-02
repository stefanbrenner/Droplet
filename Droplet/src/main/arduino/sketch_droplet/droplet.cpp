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

// include arduino types and constants
#include <Arduino.h>

#include "droplet.h"
#include "logging.h"


Droplet droplet;


/*
 * Adds two actions to droplet. One HIGH action at offset and one 
 * LOW action at offset + duration.
 */
void addActions(int deviceNumber, int offset, int duration) {

  // TODO add exception handling (i.eno more memory)
  
  // opening action
  Action *actionHigh = (Action*) malloc(sizeof(struct Action));
  actionHigh->offset = offset;
  actionHigh->mode = HIGH;
  actionHigh->pin = deviceNumber;
  actionHigh->next = NULL;
  addAction(actionHigh);

  // closing action
  Action *actionLow = (Action*) malloc(sizeof(struct Action));
  actionLow->offset = offset + duration;
  actionLow->mode = LOW;
  actionLow->pin = deviceNumber;
  actionLow->next = NULL;
  addAction(actionLow);
  
}

/*
 * Add a new action to droplet
 * Actions are sorted by their offset in ascending order
 */
void addAction(Action *newAction) {
  Action *action = droplet.actions;
  
  // first action
  if(action == NULL) {
    droplet.actions = newAction;
    return;
  }
  
  // add action at first position
  if(action->offset > newAction->offset) {
    droplet.actions = newAction;
    newAction->next = action;
    return;
  }
  
  // go to correct position
  while(action->next != NULL && action->next->offset <= newAction->offset) {
    action = action->next;
  }
  
  // insert at new position
  newAction->next = action->next;
  action->next = newAction;
  
}

/*
 * Execute all actions of droplet at the right time
 */
void executeActions() {
  
  unsigned long startMillis = millis();
  
  Action *nextAction = droplet.actions;
  while(nextAction != NULL) {
  
    unsigned long currentMillis = millis();
    
    // calculate current offset
    unsigned int currentOffset = (currentMillis - startMillis); // / 100; //convert to seconds for testing purposes
    
    // execute all actions that fit current offset
    while(nextAction != NULL && nextAction->offset <= currentOffset) {
      
      // set pin to the defined mode
      digitalWrite(nextAction->pin, nextAction->mode);
      
      // goto next action
      nextAction = nextAction->next;
    }
    
  }
}

/*
 * Simple print function for all actions
 */
void printActions() {
  Action *nextAction = droplet.actions;
  while(nextAction != NULL) {
    Serial.print(nextAction->pin);
    Serial.print(":");
    Serial.print(nextAction->offset);
    Serial.print(":");
    Serial.println(nextAction->mode);  
    nextAction = nextAction->next;
  }
}

/* 
 * Removes all actions from droplet and releases their memory
 */
void clearActions() {
  Action *action = droplet.actions;  

  while(action != NULL) {
    Action *next = action->next;
    free(action);
    action = next;
  }
  
  droplet.actions = NULL;
}


