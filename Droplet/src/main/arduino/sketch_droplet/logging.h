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

#ifndef __LOGGING_H__
#define __LOGGING_H__


// logging message levels
#define ERROR 0
#define WARN 1
#define INFO 2
#define DEBUG 3


// actual logging level
extern int loggingLevel;

/*
 * Print a logging message to serial
 */
void logging(const int level, const char* message);

/*
 * Set logging level
 */
void setLoggingLevel(const int level);



#endif
