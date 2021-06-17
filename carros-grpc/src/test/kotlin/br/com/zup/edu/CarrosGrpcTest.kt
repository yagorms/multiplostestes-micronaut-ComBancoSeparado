package br.com.zup.edu
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION,
    transactional = false
)
class CarrosGrpcTest {

    /**
     * Estrategias:
     * louça: sujou, limpou -> @AfterEach
     * louça: limpou, usou -> @BeforeEach
     * louça: usa louça descartavel -> rollback=true
     * louça: usa a louça, jogo fora, compro nova -> recriar o banco a cada teste
     */

    @BeforeEach
    fun setup(){
        repository.deleteAll()
    }

    @AfterEach
    fun cleanUp(){
        repository.deleteAll()
    }

    @Inject
    lateinit var repository: CarroRepository

    @Test
    fun `deve inserir um novo carro`() {

        // cenário

        // ação
        repository.save(Carro("gol", "HPX-1234"))

        // validação
        assertEquals(1, repository.count())
    }

    @Test
    fun `deve encontrar carro por placa`(){

        // cenário
        repository.save(Carro("palio", "OIP-9876"))

        // ação
        val encontrado = repository.existsByPlaca("OIP-9876")

        // validação
        assertTrue(encontrado)
    }
}
