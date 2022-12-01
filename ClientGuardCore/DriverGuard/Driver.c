#include  <ntddk.h>
#include "Protect/Protect.h"
#include "../Sharded/Version.h"

DRIVER_INITIALIZE DriverEntry;

VOID DriverUnload(PDRIVER_OBJECT DriverObject) {
    //Camo Content
}

NTSTATUS DriverEntry(PDRIVER_OBJECT DriverObject, PUNICODE_STRING RegistryPath) {
    //Camo Content

    return STATUS_SUCCESS;
}
