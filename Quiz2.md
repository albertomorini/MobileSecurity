
## Quiz lecture 2

1) what are the main features of the system apps?
    a) they are considered more secure than user installed apps, they cannot be unininstalled, they are installed under the /system partition
    -- can have special permission, and be customized by the phone vendor

2) an android app very likely has
    a) a unique UID and multiple GIDs 

3) system service provide uniqe Android features and
    b) they run in dedicated process
-- IPC protocol of communication between two apps


4) the android sanbox model guarantees isolation
    a) at both the process and th system file level

5) An android app has many entry points
    a) as the number of exported components declared in the manifest file

    -- entry point = component need to be exported 
    -- exported = another app can call it


6) The Adnroid OS is event-driven, which means that
    a) whenever there is an event, the AndroisOS find the components able to handle it and fowards the evento to them


7) Explicit intents
    c) more secure thanimplicit ones

-- with the same package-name can be intercept data from ITC (from another apps)

8) App signature
    a) without any trust toward to the developer

9) android permissions
    b) can be changed after the pap installation if the app is updated, too

10) android permission
    a) are granted at different times according to their severity level 



-- adb logcat -s MOBIOTSEC


