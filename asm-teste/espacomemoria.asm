
_start:
    mov r0, 100      # endereço de memória 100
    mov r1, 55       # valor a ser armazenado
    store [r0], r1   # memoria[100] = 55
    load r2, [r0]    # r2 = memoria[100]
    mov r0, 0
    syscall