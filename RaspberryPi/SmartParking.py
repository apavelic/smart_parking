#!/usr/bin/env python

import RPi.GPIO as GPIO
import time
import socket


MAX_DISTANCE = 5
PORT = 1234
HOST = "192.168.1.7"

#sensor 1
TRIG1 = 23
ECHO1 = 24
current_distance1 = 0
firstTime1 = True

#sensor 2
TRIG2 = 25
ECHO2 = 12
current_distance2 = 0
firstTime2 = True

#sensor 3
TRIG3 = 20
ECHO3 = 21
current_distance3 = 0
firstTime3 = True

#sensor 4
TRIG4 = 5
ECHO4 = 6
current_distance4 = 0
firstTime4 = True

print "Distance measurement in progress"

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))
sock.close()  

def getDistance(trig, echo):
  setupSensors(trig, echo)

  GPIO.output(trig, False)

  time.sleep(0.1)  
  
  GPIO.output(trig, True)
  time.sleep(0.00001)
  GPIO.output(trig, False)
  
  while GPIO.input(echo)==0:               
    pulse_start = time.time()
  while GPIO.input(echo)==1:               
    pulse_end = time.time()
  
  
  pulse_duration = pulse_end - pulse_start 

  distance = pulse_duration * 17150        
  distance = round(distance, 2)
  GPIO.cleanup()
  return distance

def setupSensors(trig, echo):
  GPIO.setmode(GPIO.BCM) 
  GPIO.setup(trig,GPIO.OUT)
  GPIO.setup(echo,GPIO.IN)

def send(sensorId, status):
  sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  sock.connect((HOST, PORT))
  sock.send(str(sensorId) + "\n")
  sock.send(status + "\n")
  sock.close()

def saveChanges(sensorId, distance, current_distance):
  if current_distance < MAX_DISTANCE and distance > MAX_DISTANCE:
    print "ID:", sensorId, "- FREE"
    send(sensorId, "FREE")
    
  elif current_distance > MAX_DISTANCE and distance < MAX_DISTANCE:
    print "ID:", sensorId, "- NOT FREE"
    send(sensorId, "NOTFREE")

while True:
  try:
    #sensor 1
    distance = getDistance(TRIG1, ECHO1)
    if firstTime1:
      current_distance1 = distance
      firstTime1 = False
    else:    
      saveChanges(1, distance, current_distance1)      
      current_distance1 = distance

    #sensor 2
    distance = getDistance(TRIG2, ECHO2)
    if firstTime2:
      current_distance2 = distance
      firstTime2 = False
    else:    
      saveChanges(2, distance, current_distance2)   
      current_distance2 = distance

    #sensor 3
    distance = getDistance(TRIG3, ECHO3)
    if firstTime3:
      current_distance3 = distance
      firstTime3 = False
    else:    
      saveChanges(3, distance, current_distance3)      
      current_distance3 = distance

    #sensor 4
    distance = getDistance(TRIG4, ECHO4)
    if firstTime4:
      current_distance4 = distance
      firstTime4 = False
    else:    
      saveChanges(4, distance, current_distance4)      
      current_distance4 = distance
  except:
    print("Server is offline")
