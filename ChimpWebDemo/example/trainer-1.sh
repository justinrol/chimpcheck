adb -s emulator-5554 uninstall plv.colorado.edu.chimptrainer
adb -s emulator-5554 uninstall plv.colorado.edu.chimptrainer.test
adb -s emulator-5554 install $(dirname "$0")/trainer/app-debug.apk
adb -s emulator-5554 install $(dirname "$0")/trainer/app-debug-androidTest.apk
adb -s emulator-5554 shell am instrument -r -w -e debug false -e eventTrace CgsIARIHCAY6AwjoBwoTCAESDwgBEgsKCQgCGgVCZWdpbgocCAESGAgFMhQKDAgCGgh1c2VybmFtZRIEdGVzdAocCAESGAgFMhQKDAgCGghwYXNzd29yZBIEdGVzdAoTCAESDwgBEgsKCQgCGgVMb2dpbgobCAESFwgBEhMKEQgCGg1Td2lwZSBUZXN0aW5nChYIARISCAQqDgoICAEQ7oCs+AcSAggDChYIARISCAQqDgoICAEQ74Cs+AcSAggCChYIARISCAQqDgoICAEQ7oCs+AcSAggDChYIARISCAQqDgoICAEQ7oCs+AcSAggCChYIARISCAQqDgoICAEQ74Cs+AcSAggDChYIARISCAQqDgoICAEQ7YCs+AcSAggCChYIARISCAQqDgoICAEQ7oCs+AcSAggDChYIARISCAQqDgoICAEQ7oCs+AcSAggCChYIARISCAQqDgoICAEQ7oCs+AcSAggDChYIARISCAQqDgoICAEQ74Cs+AcSAggCChYIARISCAQqDgoICAEQ7YCs+AcSAggDChYIARISCAQqDgoICAEQ7YCs+AcSAggCChYIARISCAQqDgoICAEQ7oCs+AcSAggDChYIARISCAQqDgoICAEQ7YCs+AcSAggCChYIARISCAQqDgoICAEQ7YCs+AcSAggDChYIARISCAQqDgoICAEQ7YCs+AcSAggCChYIARISCAQqDgoICAEQ7oCs+AcSAggDChYIARISCAQqDgoICAEQ74Cs+AcSAggCChYIARISCAQqDgoICAEQ74Cs+AcSAggDChYIARISCAQqDgoICAEQ74Cs+AcSAggCCgYIARICCAcKBggBEgIIBwoLCAESBwgGOgMIiCcKBggCGgIIAQ== -e appPackageName plv.colorado.edu.chimptrainer -e class plv.colorado.edu.chimptrainer.TestExpresso plv.colorado.edu.chimptrainer.test/edu.colorado.plv.chimp.driver.ChimpJUnitRunner