import dao.ClienteMapDAO;
import dao.IClienteDAO;
import domain.Cliente;

import javax.swing.*;
import java.util.Collection;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        String opcao = JOptionPane.showInputDialog(null,
                "Digite 1 para cadastro, 2 para consultar, 3 para exclusão, 4 para alteração, 5 para mostrar lista ou 6 para sair",
                "Cadastro",
                JOptionPane.INFORMATION_MESSAGE);

        //Enquanto a opção for inválida
        while(!isOpcaoValida(opcao)){
            if("".equals(opcao)){
                sair();
            }

            opcao = JOptionPane.showInputDialog(null,
                    "Opção inválida, digite 1 para cadastro, 2 para consultar, 3 para exclusão, 4 para alteração, 5 para mostrar lista ou 6 para sair",
                    "Cadastro",
                    JOptionPane.INFORMATION_MESSAGE);
        }



        //Se a opção for válida
        while(isOpcaoValida(opcao)){

            String method = methodSelected(opcao);

            if (method.equals("cadastro")) {
                String dados = JOptionPane.showInputDialog(null,
                        "Digite os dados do cliente separando-os por vírgula, conforme exemplo: Nome, CPF, Tel, Endereco, Número, Cidade e Estado",
                        "Cadastro",
                        JOptionPane.INFORMATION_MESSAGE);
                cadastrar(dados);
            }
            else if(isOpcaoUsuarioExistente(opcao)){
                //Title to be inserted to modal
                String title = modalTitle(opcao);

                String cpfRebecebido = JOptionPane.showInputDialog(null,
                        "Digite o CPF",
                        title,
                        JOptionPane.INFORMATION_MESSAGE);

                boolean cpfIsCorrect = cpfRebecebido.length() == 11;

                while(!cpfIsCorrect) {
                    cpfRebecebido = JOptionPane.showInputDialog(null,
                            "CPF incorreto, por favor tente novamente(sem pontuações ou espaços).",
                            title,
                            JOptionPane.ERROR_MESSAGE);
                    cpfIsCorrect = cpfRebecebido.length() == 11;
                }

                switch (method) {
                    case "consultar":
                        consultar(cpfRebecebido);
                        break;
                    case "excluir":
                        exluir(cpfRebecebido);
                        break;
                    case "alterar":
                        alterar(cpfRebecebido);
                }
            }
            else if(method.equals("mostrarLista")){
                Collection<Cliente> listaCompleta = iClienteDAO.buscarTodos();
                    System.out.println("==== Lista de clientes ====");
                for (Cliente cliente : listaCompleta) {
                    System.out.println(cliente);
                }
            }
            else if(method.equals("sair")){
                sair();
            }

            opcao = JOptionPane.showInputDialog(null,
                    "Digite 1 para cadastro, 2 para consultar, 3 para exclusão, 4 para alteração, 5 para mostrar lista ou 6 para sair",
                    "Cadastro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Retorna se a opção é válida ou não (true/false)
    private static boolean isOpcaoValida(String opcao){
        return switch (opcao) {
            case "1", "2", "3", "4", "5", "6" -> true;
            default -> false;
        };
    }

    //Métodos de verificação
    private static String methodSelected(String opcao){
        return switch (opcao){
            case "1" -> "cadastro";
            case "2" -> "consultar";
            case "3" -> "excluir";
            case "4" -> "alterar";
            case "5" -> "mostrarLista";
            case "6" -> "sair";
            default -> "opcaoInvalida";
        };
    }

    private static boolean isOpcaoUsuarioExistente(String opcao){
        return switch (opcao){
            case "2", "3", "4" -> true;
            default -> false;
        };
    }

    private static String modalTitle(String optionSelected){
        return switch (optionSelected){
            case "2" -> "Consultar";
            case "3" -> "Excluir";
            case "4" -> "Alterar";
            default -> null;
        };
    }

    //Métodos de interação
    private static void cadastrar(String dados){
        String[] dadoSeparado = dados.split(",|,\\s");

        Cliente cliente = new Cliente(dadoSeparado[0], dadoSeparado[1], dadoSeparado[2], dadoSeparado[3], dadoSeparado[4], dadoSeparado[5], dadoSeparado[6]);
        Boolean isCadastrado = iClienteDAO.cadastrar(cliente);
        if(isCadastrado){
            JOptionPane.showInternalMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showInternalMessageDialog(null, "Cliente já se encontra cadastrado", "Erro", JOptionPane.WARNING_MESSAGE);
        }
//        List<String> dadoFinal = new ArrayList<>();

//        Atribuindo meus dados separados para meu dados final
//        for(int i = 0; i < Cliente.class.getFields().length; i++){
//            if(dadoSeparado[i] == null){
//            dadoFinal.add("null");
//            }
//                dadoFinal.add(dadoSeparado[i]);
//        }
//
//        Cliente cliente = new Cliente(
//                dadoFinal.get(0),
//                dadoFinal.get(1),
//                dadoFinal.get(2),
//                dadoFinal.get(3),
//                dadoFinal.get(4),
//                dadoFinal.get(5),
//                dadoFinal.get(6)
//                );
//        JOptionPane.showInputDialog(cliente);
    }

    private static void consultar(String cpfInformado){

        Cliente cliente = iClienteDAO.consultar(Long.parseLong(cpfInformado));

        if(cliente != null){
            JOptionPane.showInternalMessageDialog(null, "Cliente encontrado." + cliente.toString(), "Cliente encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showInternalMessageDialog(null, "Cliente não encontrado.", "Cliente não encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void exluir(String cpfInformado){
        boolean clienteWasDeleted = iClienteDAO.excluir(Long.parseLong(cpfInformado));

        if(clienteWasDeleted){
            JOptionPane.showInternalMessageDialog(null,"Cliente deletado com sucesso");
        } else{
            JOptionPane.showInternalMessageDialog(null,"CPF não encontrado, por favor, revise os dados", "Exlusão de dados", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void alterar(String cpfInformado){
        Cliente clienteExiste = iClienteDAO.consultar(Long.parseLong(cpfInformado));

        if(clienteExiste != null){
            String newData = JOptionPane.showInputDialog(null,
                    "Insira os novos dados do cliente"
                    , "Cliente encontrado",
                    JOptionPane.INFORMATION_MESSAGE);

            String[] splitedData = newData.split(",|,\\s");
            Cliente novosDadosDoCliente = new Cliente(splitedData[0], splitedData[1], splitedData[2], splitedData[3], splitedData[4], splitedData[5], splitedData[6]);
            iClienteDAO.alterar(Long.parseLong(cpfInformado), novosDadosDoCliente);

            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
        }else{
            JOptionPane.showMessageDialog(null, "Cliente não encontrado", "Alteração de dados", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void sair(){
        JOptionPane.showInternalMessageDialog(null, "Até logo...", "Sair", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
