// *************************************************************************************
// Projeto Desafio Kotlin
// Autor: Lucio P. Patrocínio
// Data Criacao: 09/05/2023
// Data Ultima alteracao: 09/05/2023
// *************************************************************************************
// Descrição: Alteração do template disponibilizado para o projeto Kotlin.
// Este projeto procura abstrair as FORMACOES da DIO e outras entidades associadas na
// forma de classes em Kotlin.
// *************************************************************************************

// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)


// Niveis atribuidos a formação e ao conteudo educacional
enum class Nivel(val valor:Int) { BASICO(0), INTERMEDIARIO(1), DIFICIL(2), INDETERMINADO(3) }

data class Usuario(val nome:String)

open class ConteudoEducacional(val nome:String){
    open var duracao:Int=60
    open var nivel:Nivel = Nivel.BASICO
}


data class Atividade(val nome: String, val cursos:List<ConteudoEducacional>)

class Curso( nome:String):ConteudoEducacional(nome = nome){
    val aulas = mutableListOf<Aula>()
    override var duracao:Int = 0
    override var nivel: Nivel = super.nivel


    fun adicionaAula(aula:Aula){
        aulas.add(aula)
        totalizaDuracao()
        determinaNivel()
    }
    private fun totalizaDuracao(){
        var totalHoras = aulas.sumOf { it.duracaoHoras }
        var totalMinutos = aulas.sumOf { it.duracaoMinutos }
        var totalSegundos = aulas.sumOf { it.duracaoSegundos }

        if (totalSegundos>60) totalMinutos += totalSegundos / 60
        if (totalMinutos >60) totalHoras+=totalMinutos/60
        if (totalHoras<1) totalHoras=1
        duracao=totalHoras
    }

    private fun determinaNivel(){
        val duracaoTotal = aulas.sumOf { it.duracaoHoras*3600+it.duracaoMinutos*60+it.duracaoSegundos }
        val valorNivel:Int = aulas.sumOf{it.nivel.valor*(it.duracaoHoras*3600+it.duracaoMinutos*60+it.duracaoSegundos)}/duracaoTotal
        nivel = when(valorNivel){
            Nivel.BASICO.valor -> Nivel.BASICO
            Nivel.INTERMEDIARIO.valor->Nivel.INTERMEDIARIO
            Nivel.DIFICIL.valor -> Nivel.DIFICIL
            else -> Nivel.INDETERMINADO
        }

    }

    override fun toString():String{
        var texto:String
        var contador:Int = 1
        texto = "Curso: ${nome} \n"
        texto += "Duracao: ${duracao} hora(s)\n"
        texto += "Nivel: ${nivel}\n"
        texto += "--------------------------------------------------------------------\n"
        for(aula in aulas){
            texto+= "\tAula ${contador}: " + aula.toString() + "\n"
            contador++
        }

        return texto
    }
}

data class Aula(val nome:String, val duracaoHoras:Int=0, val duracaoMinutos:Int, val duracaoSegundos:Int, val nivel:Nivel){
    override fun toString():String {

        return "${nome} - Duracao: $duracaoHoras:$duracaoMinutos:$duracaoSegundos - Nivel: $nivel"
    }
}

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>,val nivel:Nivel) {

    private val inscritos  = mutableListOf<Usuario>()
    
    fun matricular(usuario: Usuario) {
        //TODO("Utilize o parâmetro $usuario para simular uma matrícula (usar a lista de $inscritos).")
        inscritos.add(usuario)
    }
}

fun main() {
    val linhaSeparacao = "--------------------------------------------------------------------"
    // Criando novos conteudos educacionais
    //conteudo1 = ConteudoEducacional(,,)

    // Criando nova formacao
    //formacao = Formacao("Formacao Python Developer",conteudos,Nivel.INTERMEDIARIO)

    // Criando um curso
    val curso:Curso = Curso("Ambiente de Desenvolvimento e Primeiros Passos com Python")
//    println(curso.nome)

    curso.adicionaAula(Aula("Boas Vindas",0,1,30,Nivel.BASICO))
    curso.adicionaAula(Aula("Conceitos Basicos", 0, 15,30,Nivel.BASICO))
    curso.adicionaAula(Aula("Classes",0,10,50,Nivel.INTERMEDIARIO))
    curso.adicionaAula(Aula("Funcoes", 0,9,10,Nivel.INTERMEDIARIO))
    curso.adicionaAula(Aula("Colecoes", 1,0,0,Nivel.DIFICIL ))


    println(linhaSeparacao)
    println(curso)
    println(linhaSeparacao)


//    TODO("Analise as classes modeladas para este domínio de aplicação e pense em formas de evoluí-las.")
//   TODO("Simule alguns cenários de teste. Para isso, crie alguns objetos usando as classes em questão.")
}

