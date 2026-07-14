

public class Monociclo{
    static short[] memoria;                      // memoria de instrucao e dados
    static short[] registradores = new short[8]; // 8 registradores (3 para endereco bits)               
    static int programaContador = 1;             // PC(endereco) qual instrucao e executada comecar sempre em 1
    static lib biblioteca = new lib();           // ler os bits, extrair bits, write memory...

    public static void main(String[] args){
	 memoria = biblioteca.load_binary("C:/Users/User/Downloads/bin/count.bin");
     
     while(programaContador < memoria.length){
        
        int enderecoExecutado = programaContador;  // guarda ANTES, pois jump/jump_cond mudam programaContador

        short instrucaoAtual = buscarInstrucao();
        short[] instrucao = decodificarInstrucao(instrucaoAtual);

        short[] antes = registradores.clone();      // copia antes de executar
        execucaoInstrucao(instrucao);

        imprimirRegistradores(antes, enderecoExecutado);

        programaContador++;
     }
     
    }
    public static short buscarInstrucao(){
        return memoria[programaContador];
    }

    public static short[] decodificarInstrucao(short instrucao){
        short formato = biblioteca.extract_bits(instrucao, 15, 1);
        if(formato == 0){
            short opcode = biblioteca.extract_bits(instrucao, 9, 6);
            short destino = biblioteca.extract_bits(instrucao, 6, 3);
            short operando1 = biblioteca.extract_bits(instrucao, 3, 3);
            short operando2 = biblioteca.extract_bits(instrucao, 0, 3);
            short[] instrucaoDecodificada = {formato, opcode, destino, operando1, operando2};
            return instrucaoDecodificada;
        }  else{

            short opcode = biblioteca.extract_bits(instrucao, 13, 2);
            short registrador = biblioteca.extract_bits(instrucao, 10, 3);
            short imediato = biblioteca.extract_bits(instrucao, 0, 10);
            short[] instrucaoDecodificada = {formato, opcode, registrador, imediato};
            return instrucaoDecodificada;
        }
    }

    public static void execucaoInstrucao(short[] instrucao){
        if(instrucao[0] == 0){
            switch(instrucao[1]){
                case 0:
                add(instrucao[2], instrucao[3], instrucao[4]);
                break;

                case 1: 
                sub(instrucao[2], instrucao[3], instrucao[4]);
                break;

                case 2:
                mul(instrucao[2], instrucao[3], instrucao[4]);
                break;

                case 3:
                div(instrucao[2], instrucao[3], instrucao[4]);
                break;

                case 4:
                cmp_equal(instrucao[2], instrucao[3], instrucao[4]);
                break;

                case 5: 
                cmp_neq(instrucao[2], instrucao[3], instrucao[4]);
                break;

                case 15:
                load(instrucao[2], instrucao[3]);
                break;

                case 16:
                store(instrucao[3], instrucao[4]);
                break;

                case 63:
                syscall();
                break;
            
                default:
                break;
                
            }
        } else{
            switch (instrucao[1]) {
                case 0:
                jump(instrucao[3]);
                break;

                case 1:
                jump_cond(instrucao[2], instrucao[3]);
                break;

                case 3:
                move(instrucao[2], instrucao[3]);
                break;
            
                default:
                    break;
            }
        }
    }
    public static void add(short destino, short operando1, short operando2){
        registradores[destino] = (short) (registradores[operando1] + registradores[operando2]);
    }
    public static void sub(short destino, short operando1, short operando2){
        registradores[destino] = (short) (registradores[operando1] - registradores[operando2]);
    }
    public static void mul(short destino, short operando1, short operando2){
        registradores[destino] = (short) (registradores[operando1] * registradores[operando2]);
    }
    public static void move(short registrador, short imediato){
        registradores[registrador] = imediato; 
    }
    public static void div(short destino, short operando1, short operando2){
        registradores[destino] = (short) (registradores[operando1] / registradores[operando2]);
    }
    public static void cmp_equal(short destino, short operando1, short operando2){
        if(registradores[operando1] == registradores[operando2]){
            registradores[destino] = 1;

        } else{
            registradores[destino] = 0;
        }
    }
    public static void cmp_neq(short destino, short operando1, short operando2){
        if(registradores[operando1] != registradores[operando2]){
            registradores[destino] = 1;
        } else{
            registradores[destino] = 0;
        }
    }
    public static void load(short destino, short operando1){
        registradores[destino] = memoria[registradores[operando1]];
    }
    public static void store(short operando1, short operando2){
        memoria[registradores[operando1]] = registradores[operando2];
    }
    public static void jump(short imediato){
        programaContador = imediato - 1;
    }
    public static void jump_cond(short registrador, short imediato){
        if(registradores[registrador] == 1){
            programaContador = imediato - 1;

        }
    }
    public static void syscall(){
        System.exit(0);

    }
    public static void imprimirRegistradores(short[] antes, int pc){
    StringBuilder sb = new StringBuilder("PC=" + pc + "  ");
    for(int i = 0; i < registradores.length; i++){
        boolean mudou = registradores[i] != antes[i];
        sb.append("R").append(i).append("=").append(registradores[i]);
        if(mudou) sb.append("[*]");
        sb.append("  ");
    }
    System.out.println(sb.toString());
}
}
