// *************************************************************************************
// Projeto Desafio Kotlin
// Autor: Lucio P. Patrocínio
// Data Criacao: 09/05/2023
// Data Ultima alteracao: 10/05/2023
// *************************************************************************************
// Descrição: Alteração do template disponibilizado para o projeto Kotlin.
// Este projeto procura abstrair as FORMACOES da DIO e outras entidades associadas na
// forma de classes em Kotlin.
// *************************************************************************************

// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)


// Niveis atribuidos a formação e ao conteudo educacional
enum class Nivel(val valor:Int) { BASICO(0), INTERMEDIARIO(1), DIFICIL(2), INDETERMINADO(3) }

open class Conteudo(val nome:String){
    open var duracaoHoras:Int=60
    open var duracaoMinutos:Int = 0
    open var duracaoSegundos:Int = 0
    open var nivel:Nivel = Nivel.BASICO
}


 open class ConteudoEducacional(nome:String):Conteudo(nome=nome) {
     open val listaConteudos = mutableListOf<Conteudo>()

     open fun addConteudo(conteudo: Conteudo) {
         listaConteudos.add(conteudo)
         determinarDuracao()
         determinarNivel()
     }

     open fun determinarDuracao() {
         var totalHoras = listaConteudos.sumOf { it.duracaoHoras }
         var totalMinutos = listaConteudos.sumOf { it.duracaoMinutos }
         var totalSegundos = listaConteudos.sumOf { it.duracaoSegundos }

         if (totalSegundos >= 60) {
             totalMinutos += totalSegundos / 60
             totalSegundos %= 60
         }
         if (totalMinutos >= 60) {
             totalHoras += totalMinutos / 60
             totalMinutos %= 60
         }

         if(totalMinutos>30) totalHoras += 1

         if (totalHoras < 1) totalHoras = 1

         this.duracaoHoras = totalHoras

     }

     open fun determinarNivel() {
         val duracaoTotal = listaConteudos.sumOf { it.duracaoHoras*3600+it.duracaoMinutos*60+it.duracaoSegundos }
         val valorNivel: Int = listaConteudos.sumOf { it.nivel.valor * (it.duracaoHoras * 3600 + it.duracaoMinutos * 60 + it.duracaoSegundos) } / duracaoTotal

             this.nivel = when (valorNivel) {
             Nivel.BASICO.valor -> Nivel.BASICO
             Nivel.INTERMEDIARIO.valor -> Nivel.INTERMEDIARIO
             Nivel.DIFICIL.valor -> Nivel.DIFICIL
             else -> Nivel.INDETERMINADO
         }

     }
 }

class Formacao(nome: String):ConteudoEducacional(nome=nome) {
    private val inscritos = mutableListOf<Usuario>()
    override var duracaoHoras: Int=super.duracaoHoras
    override var nivel =super.nivel


    fun matricular(usuario: Usuario) {
        inscritos.add(usuario)
    }

    override fun toString(): String {
        var texto:String= "===============================================================================\n"
        texto += "Formacao: ${nome}\nCH: ${duracaoHoras} hora(s) - Nivel: ${nivel}\n"

        for (atividade in listaConteudos){
            texto+=atividade.toString() + "\n"
        }
        texto+=listarUsuario()
        texto +="===============================================================================\n"
        return texto
    }

    fun listarUsuario():String{
        var texto:String = "Lista de Estudantes Inscritos\n"

        texto+="------------------------------------------------------------------------------\n"
        var contador:Int=1
        for(aluno in inscritos.sortedBy { it.nome }){
            texto+= "${contador} - \t${aluno.nome}\n"
            contador++
        }
        return texto
    }
}

data class Usuario(val nome:String)

class Atividade(nome: String):ConteudoEducacional(nome=nome){
    override var duracaoHoras: Int = super.duracaoHoras
    override var nivel = super.nivel

    override fun toString():String{
        var texto:String= "------------------------------------------------------------------------------\n"
        texto += "Atividade: ${this.nome}\n"
        texto += "Duracao: ${this.duracaoHoras} hora(s)\n"
        texto += "Nivel: ${this.nivel}\n"
        texto += "------------------------------------------------------------------------------\n"
        for(curso in listaConteudos){
            texto+= curso.toString() + "\n"
        }
        return texto
    }
}

class Curso( nome:String):ConteudoEducacional(nome = nome){
    override fun toString():String{
        var texto:String
        var contador:Int = 1
        texto = "\tCurso: ${nome} \n"
        texto += "\tDuracao: ${duracaoHoras} hora(s)\n"
        texto += "\tNivel: ${nivel}\n"
        texto += "------------------------------------------------------------------------------\n"
        for(aula in listaConteudos){
            texto+= "\t\tAula ${contador}: " + aula.toString() + "\n"
            contador++
        }
        texto += "------------------------------------------------------------------------------"
        return texto
    }
}

 class Aula(nome:String, override var duracaoHoras:Int=0, override var duracaoMinutos:Int, override var duracaoSegundos:Int, override var nivel:Nivel):Conteudo(nome=nome){
    override fun toString():String {
        return "${nome} - Duracao: $duracaoHoras:$duracaoMinutos:$duracaoSegundos - Nivel: $nivel"
    }
}


fun main() {

    // Criando nova formacao
    val formacao:Formacao = Formacao("Formacao Python Developer")
    val curso:ConteudoEducacional = Curso("Ambiente de Desenvolvimento e Primeiros Passos com Python")
    val atividade:ConteudoEducacional = Atividade("Fundamentos de Python")
    val curso2:ConteudoEducacional = Curso("Conhecendo a Linguagem Python")

    // Associando aulas a cursos
    curso.addConteudo(Aula("Boas Vindas",0,1,30,Nivel.BASICO))
    curso.addConteudo(Aula("Conceitos Basicos", 0, 15,30,Nivel.BASICO))
    curso.addConteudo(Aula("Classes",0,10,50,Nivel.INTERMEDIARIO))
    curso.addConteudo(Aula("Funcoes", 0,9,10,Nivel.INTERMEDIARIO))
    curso.addConteudo(Aula("Colecoes", 1,0,0,Nivel.DIFICIL ))

    curso2.addConteudo(Aula("Nocoes iniciais",0,25,30,Nivel.BASICO))
    curso2.addConteudo(Aula("Criando uma pilha", 0, 50,15,Nivel.INTERMEDIARIO))
    curso2.addConteudo(Aula("Lista Encadeada",0,40,50,Nivel.DIFICIL))


    // Associando cursos a atividades
    atividade.addConteudo(curso)
    atividade.addConteudo(curso2)

    // Associando atividades a Formacao
    formacao.addConteudo(atividade)

    // alunos inscritos
    formacao.matricular(Usuario("Joao Santana"))
    formacao.matricular(Usuario("Alice Leiva"))
    formacao.matricular(Usuario("Jesus de Padua"))
    formacao.matricular(Usuario("Paulo Queiroz"))
    formacao.matricular(Usuario("Leticia Telles"))
    formacao.matricular(Usuario("Geralda Silva"))

    // Impressão do resultado da simulação
    println(formacao)



//    TODO("Analise as classes modeladas para este domínio de aplicação e pense em formas de evoluí-las.")
//   TODO("Simule alguns cenários de teste. Para isso, crie alguns objetos usando as classes em questão.")
}

