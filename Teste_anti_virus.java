package Principal;

//***********************
//--Informações básicas--
//
//Path -> Caminho
//File -> Arquivo
//
//***********************
//Bibliotecas utilizadas no código

//Trata os erros relacionados a entrada e saídas de dados
import java.io.IOException;
//Manipulação de arquivos e diretórios
import java.nio.file.*;
//Dados sobre os arquivos e diretórios
import java.nio.file.attribute.BasicFileAttributes;

public class Teste_anti_virus {

    public static void main(String[] args) {
        
        Path caminhoRaiz = Paths.get("C:\\");  // Diretório de início para busca, nesse caso a unidade de armazenamento "C"

        // Nome dos arquivos maliciosos que estamos procurando
        String nomeArquivo1 = "script-aula-teste.bat";
        String nomeArquivo2 = "script-aula.bat";
        String nomeArquivo3 = "execucao-aula-teste.exe";
        String nomeArquivo4 = "execucao-aula.exe";
        
        // Chamando o método "buscarArquivo"
        // Buscar e deletar os arquivos
        buscarArquivo(caminhoRaiz, nomeArquivo1);
        buscarArquivo(caminhoRaiz, nomeArquivo2);
        buscarArquivo(caminhoRaiz, nomeArquivo3);
        buscarArquivo(caminhoRaiz, nomeArquivo4);
    }    

    // Método que procura o arquivo, imprime seu caminho e o deleta
    public static void buscarArquivo(Path caminhoRaiz, String nomeArquivo) {
        try {
        	
            // Variável de controle para verificar se o arquivo foi encontrado
        	//Começará como falsa porque a busca pelo arquivo ainda nem começou
            final boolean arquivoEncontrado[] = {false} ;

            // Usando Files.walkFileTree para percorrer o diretório e subdiretórios
            Files.walkFileTree(caminhoRaiz, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Verifica se o arquivo encontrado é o que estamos procurando
                    if (file.getFileName().toString().equalsIgnoreCase(nomeArquivo)) {
                        // Imprime o caminho do arquivo encontrado
                        System.out.println("Arquivo encontrado: " + file.toString());
                        
                        Files.delete(file);  // Deleta o arquivo
                        System.out.println("Arquivo deletado: " + file.toString());
                        
                        arquivoEncontrado[0] = true;  // Marca que o arquivo foi encontrado

                        return FileVisitResult.TERMINATE;  // Para a busca assim que o arquivo for encontrado e deletado
                    }
                    return FileVisitResult.CONTINUE;  // Continua buscando
                }

             // Em caso de erro ao acessar o arquivo ou diretório, continua a busca
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });

            // Se o arquivo não foi encontrado após a busca, imprime uma mensagem
            if (arquivoEncontrado[0] == false) {
                System.out.println("Arquivo " + nomeArquivo + " não encontrado.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao procurar o arquivo: " + e.getMessage());
        }
    }
}