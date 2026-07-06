public class Monociclo{
    static short[] instrucaoMemoria;             // memoria de instrucao
    static short[] registradores = new short[8]; // 8 registradores (3 bits)
    static short[] memoriaDados;                 // memoria de dados para store e load
    static int programaContador = 1;             // PC(endereco) qual instrucao e executada comecar sempre em 1
    static lib biblioteca = new lib();           // ler os bits, extrair bits, write memory...


    public static void main(String[] args){
	 instrucaoMemoria = biblioteca.load_binary("C:/Users/55449/Desktop/cpu-sim/bin-program/teste.bin");
     memoriaDados = biblioteca.get_dado_Memoria();
     while(programaContador < instrucaoMemoria.length){
        short instrucaoAtual = buscarInstrucao();

        short[] instrucao = decodificarInstrucao(instrucaoAtual);

        execucaoInstrucao(instrucao);

        programaContador++;
     }
     
    }
    public static short buscarInstrucao(){
        return instrucaoMemoria[programaContador];
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
                case 1: 
                sub(instrucao[2], instrucao[3], instrucao[4]);
                case 2:
                mul(instrucao[2], instrucao[3], instrucao[4]);
                
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
    
}
