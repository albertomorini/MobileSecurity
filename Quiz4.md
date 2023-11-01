
Activities
    a. are the only way for a user to interact with an app


Activities transitions between states are  
    triggered by the user and by events sent by the OS


Services
    should be started through explicit intents


A bound service keeps running
    until one of the calling component is running


If a calling component wants to communicate with a service, it should rely on 
    messengers or AIDL

Broadcast receivers 
    intercept specific system wide events

A content provider authority can handle
    multiple tables of the same content provider

Content Providers
    do not depend from the underlying structure of the data source