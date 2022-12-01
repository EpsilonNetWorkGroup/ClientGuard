#include "net_playl_clientguard_shared_ClientGuard.h"
#include <windows.h>

JNIEXPORT jboolean JNICALL Java_net_playl_clientguard_shared_ClientGuard_isAdmin
(JNIEnv*, jclass) {
    BOOL                     IsAdmin = FALSE;
    PSID                     AdminSid = nullptr;
    HANDLE                   ProcessHandle = nullptr;
    DWORD                    SizeOfTokenGroupsBuffer = 0;
    PTOKEN_GROUPS            TokenGroupsBuffer = nullptr;
    SID_IDENTIFIER_AUTHORITY SystemSidAuthority = SECURITY_NT_AUTHORITY;
    if (!OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY, &ProcessHandle)) {
        return FALSE;
    }

    if (GetTokenInformation(ProcessHandle, TokenGroups, NULL, 0, &SizeOfTokenGroupsBuffer)) {
        return FALSE;
    }

    if (GetLastError() != ERROR_INSUFFICIENT_BUFFER) {
        return FALSE;
    }

    if (!(TokenGroupsBuffer = reinterpret_cast<PTOKEN_GROUPS>(malloc(SizeOfTokenGroupsBuffer)))) {
        return FALSE;
    }

    if (!GetTokenInformation(ProcessHandle, TokenGroups, TokenGroupsBuffer, SizeOfTokenGroupsBuffer, &SizeOfTokenGroupsBuffer)) {
        return FALSE;
    }

    if (!AllocateAndInitializeSid(&SystemSidAuthority, 2, SECURITY_BUILTIN_DOMAIN_RID, DOMAIN_ALIAS_RID_ADMINS, 0, 0, 0, 0, 0, 0, &AdminSid)) {
        return FALSE;
    }

    for (DWORD index = 0; index < TokenGroupsBuffer->GroupCount; index++) {
        if (EqualSid(TokenGroupsBuffer->Groups[index].Sid, AdminSid) && ((TokenGroupsBuffer->Groups[index].Attributes) & SE_GROUP_ENABLED)) {
            IsAdmin = TRUE;
            break;
        }
    }

    FreeSid(AdminSid);

    return IsAdmin;
}