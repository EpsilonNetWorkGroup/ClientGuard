#pragma once
#include <phnt_windows.h>
#include <phnt.h>
#include <aclapi.h>

static const TCHAR* STR_SERVICE_NAME = TEXT("DriverGuard");
static const TCHAR* STR_DISPLAY_NAME = TEXT("ClientGuard Anti-Cheat Service");

BOOLEAN IsServicesInstalled();
BOOLEAN IsServicesRunning();
BOOLEAN StartServices();
BOOLEAN StopServices();
BOOLEAN CreateServices(char* driverDir);
BOOLEAN RemoveService();