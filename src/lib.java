import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class lib {
	short [] memoria = new short[256];
	int quantidadeInstrucoes;

	
	public short extract_bits (short value, int bstart, int blength)
	{
		short mask = (short)((1 << blength) - 1);
		return (short)((value >> bstart) & mask);
	}

	public void memory_write (short addr, short value){
		memoria[addr] = value;
	}

	short[] load_binary (String binary_name)
	{
		try {
			FileInputStream fileInputStream = new FileInputStream(binary_name);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);

			long tamanhoArquivo = fileInputStream.getChannel().size();
			quantidadeInstrucoes = (int) (tamanhoArquivo / 2);

			for (int i = 0; i < quantidadeInstrucoes; i++) {
				int low = dataInputStream.readByte() & 0x000000FF;
				int high = dataInputStream.readByte() & 0x000000FF;
				int value = (low | (high << 8)) & 0x0000FFFF;

				this.memory_write((short)i, (short)value);
			}

			dataInputStream.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return memoria;
	}
	public int getQuantidadeInstrucoes(){
		return quantidadeInstrucoes;
	}
}