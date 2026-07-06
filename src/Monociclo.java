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
        short[] instrucao = decodificarInstrucao(programaContador);



        programaContador++;
     }
     
    }

    public static short[] decodificarInstrucao(int endereco){
        short formato = biblioteca.extract_bits(instrucaoMemoria[endereco], 15, 1);
        if(formato == 0){
            short opcode = biblioteca.extract_bits(instrucaoMemoria[endereco], 9, 6);
            short destino = biblioteca.extract_bits(instrucaoMemoria[endereco], 6, 3);
            short operando1 = biblioteca.extract_bits(instrucaoMemoria[endereco], 3, 3);
            short operando2 = biblioteca.extract_bits(instrucaoMemoria[endereco], 0, 3);
            short[] instrucaoDecodificada = {formato, opcode, destino, operando1, operando2};
            return instrucaoDecodificada;
        }  else{
            short opcode = biblioteca.extract_bits(instrucaoMemoria[endereco], 13, 2);
            short registrador = biblioteca.extract_bits(instrucaoMemoria[endereco], 10, 3);
            short imediato = biblioteca.extract_bits(instrucaoMemoria[endereco], 0, 10);
            short[] instrucaoDecodificada = {formato, opcode, registrador, imediato};
            return instrucaoDecodificada;
        }
    }

    public static void operacoesUla(short[] instrucao){
        
    }
    
}
