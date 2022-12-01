#include "DriverGuard.h"
#include "../Logger.hpp"

BOOLEAN IsServicesRunning() {
    SC_HANDLE      servciceManagerHandle = nullptr;
    SC_HANDLE      serviceHandle = nullptr;
    SERVICE_STATUS serviceStatus = {};
    BOOLEAN        State = FALSE;

    if ((servciceManagerHandle = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_ENUMERATE_SERVICE)) == NULL) {
        Log(LOG_ERROR, "�޷���SC������, �����Ƿ���Ȩ��.\n");
        goto CleanAndExit;
    }

    if ((serviceHandle = OpenService(servciceManagerHandle, STR_SERVICE_NAME, SC_MANAGER_ENUMERATE_SERVICE)) == NULL) {
        Log(LOG_ERROR, "�޷��򿪷���, δ֪����.\n");
        goto CleanAndExit;
    }

    if (!QueryServiceStatus(serviceHandle, &serviceStatus)) {
        Log(LOG_ERROR, "�޷���ѯ����״̬, δ֪����\n");
        goto CleanAndExit;
    }

    if (serviceStatus.dwCurrentState == SERVICE_RUNNING) {
        Log(LOG_DEBUG, "ServiceStatus.dwCurrentState: 0x%08X\n", serviceStatus.dwCurrentState);
        State = TRUE;
    }

CleanAndExit:
    if (servciceManagerHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);
    if (serviceHandle != NULL)
        CloseServiceHandle(serviceHandle);

    return State;
}

BOOLEAN IsServicesInstalled() {
    SC_HANDLE servciceManagerHandle = nullptr;
    SC_HANDLE serviceHandle = nullptr;
    BOOLEAN   State = FALSE;

    if ((servciceManagerHandle = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_ENUMERATE_SERVICE)) == NULL) {
        Log(LOG_ERROR, "�޷���SC������, �����Ƿ���Ȩ��.\n");
        goto CleanAndExit;
    }

    if ((serviceHandle = OpenService(servciceManagerHandle, STR_SERVICE_NAME, SC_MANAGER_ENUMERATE_SERVICE)) != NULL) {
        Log(LOG_DEBUG, "�ɹ�, ������: 0x%p\n", serviceHandle);
        State = TRUE;
    }

CleanAndExit:
    if (servciceManagerHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);
    if (serviceHandle != NULL)
        CloseServiceHandle(serviceHandle);

    return State;
}

BOOLEAN CreateServices(char* driverPath) {
    SC_HANDLE servciceManagerHandle = nullptr;
    SC_HANDLE serviceHandle = nullptr;
    BOOLEAN   State = FALSE;
    char username[50];
    DWORD usernameLenght = sizeof username;

    SECURITY_DESCRIPTOR  sd;
    PSECURITY_DESCRIPTOR psd = NULL;
    PACL                 pacl = NULL;
    PACL                 pNewAcl = NULL;
    BOOL                 bDaclPresent = FALSE;
    BOOL                 bDaclDefaulted = FALSE;
    DWORD                dwError = 0;
    DWORD                dwSize = 0;
    DWORD                dwBytesNeeded = 0;
    EXPLICIT_ACCESS      ea = {};

    if ((servciceManagerHandle = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_ALL_ACCESS)) == NULL) {
        Log(LOG_ERROR, "�޷���SC������, �����Ƿ���Ȩ��.\n");
        goto CleanAndExit;
    }

    if ((serviceHandle = CreateService(servciceManagerHandle, STR_SERVICE_NAME, STR_DISPLAY_NAME, SC_MANAGER_ALL_ACCESS, SERVICE_KERNEL_DRIVER, SERVICE_DEMAND_START, SERVICE_ERROR_NORMAL, driverPath, NULL, NULL, NULL, NULL, NULL)) == NULL) {
        Log(LOG_ERROR, "�޷����������������, δ֪����.\n");
        goto CleanAndExit;
    }

    // ����Ȩ���Ա���ͨ�û�����/�رշ���
    // Get the current security descriptor.

    if (!QueryServiceObjectSecurity(serviceHandle, DACL_SECURITY_INFORMATION, &psd, 0, &dwBytesNeeded)) {
        if (GetLastError() == ERROR_INSUFFICIENT_BUFFER) {
            dwSize = dwBytesNeeded;
            psd = (PSECURITY_DESCRIPTOR)HeapAlloc(GetProcessHeap(),
                HEAP_ZERO_MEMORY, dwSize);
            if (psd == NULL) {
                // Note: HeapAlloc does not support GetLastError.
                Log(LOG_ERROR, "HeapAlloc ʧ��\n");
                goto CleanAndExit;
            }

            if (!QueryServiceObjectSecurity(serviceHandle, DACL_SECURITY_INFORMATION, psd, dwSize, &dwBytesNeeded)) {
                Log(LOG_ERROR, "QueryServiceObjectSecurity ʧ�� (%d)\n", GetLastError());
                goto CleanAndExit;
            }
        }
        else {
            Log(LOG_ERROR, "QueryServiceObjectSecurity ʧ�� (%d)\n", GetLastError());
            goto CleanAndExit;
        }
    }

    // Get the DACL.

    if (!GetSecurityDescriptorDacl(psd, &bDaclPresent, &pacl, &bDaclDefaulted)) {
        Log(LOG_ERROR, "GetSecurityDescriptorDacl ʧ��(%d)\n", GetLastError());
        goto CleanAndExit;
    }

    // Build the ACE.

    GetUserName(username, &usernameLenght);
    BuildExplicitAccessWithName(&ea, username, SERVICE_START | SERVICE_STOP | READ_CONTROL | DELETE, SET_ACCESS, NO_INHERITANCE);

    dwError = SetEntriesInAcl(1, &ea, pacl, &pNewAcl);
    if (dwError != ERROR_SUCCESS)
    {
        Log(LOG_ERROR, "SetEntriesInAcl ʧ��(%d)\n", dwError);
        goto CleanAndExit;
    }

    // Initialize a new security descriptor.

    if (!InitializeSecurityDescriptor(&sd, SECURITY_DESCRIPTOR_REVISION)) {
        Log(LOG_ERROR, "InitializeSecurityDescriptor ʧ��(%d)\n", GetLastError());
        goto CleanAndExit;
    }

    // Set the new DACL in the security descriptor.

    if (!SetSecurityDescriptorDacl(&sd, TRUE, pNewAcl, FALSE)) {
        Log(LOG_ERROR, "SetSecurityDescriptorDacl ʧ��(%d)\n", GetLastError());
        goto CleanAndExit;
    }

    // Set the new DACL for the service object.

    if (!SetServiceObjectSecurity(serviceHandle, DACL_SECURITY_INFORMATION, &sd)) {
        Log(LOG_ERROR, "SetServiceObjectSecurity ʧ��(%d)\n", GetLastError());
        goto CleanAndExit;
    }
    else State = TRUE;

CleanAndExit:
    if (NULL != servciceManagerHandle)
        CloseServiceHandle(servciceManagerHandle);
    if (NULL != serviceHandle)
        CloseServiceHandle(serviceHandle);
    if (NULL != pNewAcl)
        LocalFree((HLOCAL)pNewAcl);
    if (NULL != psd)
        HeapFree(GetProcessHeap(), 0, (LPVOID)psd);

    return State;
}

BOOLEAN RemoveService() {
    SC_HANDLE servciceManagerHandle = nullptr;
    SC_HANDLE serviceHandle = nullptr;
    BOOLEAN   State = FALSE;

    if ((servciceManagerHandle = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_ALL_ACCESS)) == NULL) {
        Log(LOG_ERROR, "�޷���SC������, �����Ƿ���Ȩ��.\n");
        goto CleanAndExit;
    }

    if ((serviceHandle = OpenService(servciceManagerHandle, STR_SERVICE_NAME, SC_MANAGER_ALL_ACCESS)) == NULL) {
        Log(LOG_ERROR, "�޷��򿪷���, δ֪����.\n");
        goto CleanAndExit;
    }

    if (DeleteService(serviceHandle)) {
        State = TRUE;
    }

CleanAndExit:
    if (servciceManagerHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);
    if (serviceHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);

    return State;
}

BOOLEAN StartServices() {
    SC_HANDLE servciceManagerHandle = nullptr;
    SC_HANDLE serviceHandle = nullptr;
    BOOLEAN   State = FALSE;

    if ((servciceManagerHandle = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_CONNECT)) == NULL) {
        Log(LOG_ERROR, "�޷���SC������, �����Ƿ���Ȩ��.\n");
        goto CleanAndExit;
    }

    if ((serviceHandle = OpenService(servciceManagerHandle, STR_SERVICE_NAME, SERVICE_START)) == NULL) {
        Log(LOG_ERROR, "�޷��򿪷���, δ֪����.\n");
        goto CleanAndExit;
    }

    if (StartService(serviceHandle, NULL, NULL)) {
        State = TRUE;
    }
    else if (GetLastError() == ERROR_SERVICE_ALREADY_RUNNING) {
        State = TRUE;
    }
    else {
        Log(LOG_ERROR, "�޷���������, ����: %d.\n", GetLastError());
    }

CleanAndExit:
    if (servciceManagerHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);
    if (serviceHandle != NULL)
        CloseServiceHandle(serviceHandle);

    return State;
}

BOOLEAN StopServices() {
    SC_HANDLE      servciceManagerHandle = nullptr;
    SC_HANDLE      serviceHandle = nullptr;
    SERVICE_STATUS serviceStatus = {};
    BOOLEAN        State = FALSE;

    if ((servciceManagerHandle = OpenSCManager(NULL, SERVICES_ACTIVE_DATABASE, SC_MANAGER_CONNECT)) == NULL) {
        Log(LOG_ERROR, "�޷���SC������, �����Ƿ���Ȩ��... %d\n", GetLastError());
        goto CleanAndExit;
    }

    if ((serviceHandle = OpenService(servciceManagerHandle, STR_SERVICE_NAME, SERVICE_STOP)) == NULL) {
        Log(LOG_ERROR, "�޷��򿪷���, δ֪����...\n");
        goto CleanAndExit;
    }

    if (ControlService(serviceHandle, SERVICE_CONTROL_STOP, &serviceStatus)) {
        Log(LOG_DEBUG, "ServiceStatus.dwCurrentState: 0x%08X\n", serviceStatus.dwCurrentState);
        State = TRUE;
    }

CleanAndExit:
    if (servciceManagerHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);
    if (serviceHandle != NULL)
        CloseServiceHandle(servciceManagerHandle);

    return State;
}

