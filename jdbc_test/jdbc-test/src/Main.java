import model.dao.DaoFactory;
import model.dao.usuarioDao;
import model.entities.usuario;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        usuarioDao usuarioDao = DaoFactory.createusuarioDao();

        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    usuario usuario = new usuario();
                    usuario.setName(nome);
                    usuario.setEmail(email);

                    usuarioDao.insert(usuario);

                    System.out.println("Usuário cadastrado com sucesso.");
                    break;

                case 2:
                    List<usuario> lista = usuarioDao.findAll();

                    for (usuario u : lista) {
                        System.out.println(
                                "ID: " + u.getId() +
                                        " | Nome: " + u.getName() +
                                        " | Email: " + u.getEmail()
                        );
                    }
                    break;

                case 3:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Novo nome: ");
                    String novoNome = sc.nextLine();

                    System.out.print("Novo email: ");
                    String novoEmail = sc.nextLine();

                    usuario usuarioUpdate = new usuario();
                    usuarioUpdate.setId(id);
                    usuarioUpdate.setName(novoNome);
                    usuarioUpdate.setEmail(novoEmail);

                    usuarioDao.updateById(usuarioUpdate);

                    System.out.println("Usuário atualizado.");
                    break;

                case 4:
                    System.out.print("ID: ");
                    int idDelete = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Tem certeza que deseja remover este usuário? (Y/N): ");
                    String resposta = sc.nextLine();

                    if (resposta.equalsIgnoreCase("Y")) {

                        usuarioDao.deleteById(idDelete);
                        System.out.println("✅ Usuário removido com sucesso!");

                    } else if (resposta.equalsIgnoreCase("N")) {

                        System.out.println("❌ Operação cancelada.");

                    } else {

                        System.out.println("Opção inválida! Digite apenas Y ou N.");

                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        sc.close();
    }
}