adb uninstall com.nextcloud.client.test                                                                            
adb uninstall com.nextcloud.client
adb -s emulator-5554 install $(dirname "$0")/nextcloud/NextCloud_Android-debug.apk

adb -s emulator-5554 install $(dirname "$0")/nextcloud/NextCloud_Android-debug-androidTest.apk
adb -s emulator-5554 shell am instrument -r -w -e debug false -e eventTrace ChIIARIOCAESCgoICAEQwYPA+AcKJQgBEiEIBTIdCggIARD8gMD4BxIRbmNsb3VkLnphY2x5cy5jb20KGQgBEhUIBTIRCggIARCFgcD4BxIFMjIyMDMKIAgBEhwIBTIYCggIARCHgcD4BxIMMTIzMjFxd2VxYXohChIIARIOCAESCgoICAEQiYHA+AcKSggHQkYKIAgBEhwIARIYCgtpc0Rpc3BsYXllZBIJCAIaBUFsbG93EiIKEwgBEg8IARILCgkIAhoFQWxsb3cKCwgBEgcIBjoDCOgHCgYIARICCAcKFwgBEhMIARIPCg0IAhoJRG9jdW1lbnRzCgsIARIHCAY6AwjQDwoXCAESEwgBEg8KDQgCGglBYm91dC5vZHQKCwgBEgcIBjoDCNAPChcIARITCAESDwoNCAIaCUFib3V0LnR4dAoLCAESBwgGOgMI0A8KBggCGgIIAQoGCAIaAggBCgsIARIHCAY6AwjcCwoUCAESEAgBEgwKCggCGgZQaG90b3MKFwgBEhMIARIPCg0IAhoJQ29hc3QuanBnCgYIAhoCCAEKHQgBEhkIARIVChMIAhoPSHVtbWluZ2JpcmQuanBnCgYIAhoCCAEKBggCGgIIAQoiCAESHggCGhoKGAgCGhROZXh0Y2xvdWQgTWFudWFsLnBkZgoLCAESBwgGOgMI0A8KBggCGgIIAwoSCAESDggBEgoKCAgCGgRNb3ZlChcIARITCAESDwoNCAIaCURvY3VtZW50cwoLCAESBwgGOgMI6AcKFAgBEhAIARIMCgoIAhoGQ2hvb3NlCgsIARIHCAY6AwjQDwoXCAESEwgBEg8KDQgCGglEb2N1bWVudHMKIggBEh4IAhoaChgIAhoUTmV4dGNsb3VkIE1hbnVhbC5wZGYKBggCGgIIAwoLCAESBwgGOgMI0A8KEggBEg4IARIKCggIAhoETW92ZQoUCAESEAgBEgwKCggCGgZDaG9vc2UKCwgBEgcIBjoDCIgn -e appPackageName com.owncloud.android -e class com.owncloud.android.TestExpresso com.nextcloud.client.test/edu.colorado.plv.chimp.driver.ChimpJUnitRunner
