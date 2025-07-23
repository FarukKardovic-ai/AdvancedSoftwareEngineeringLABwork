package it.unibo.basics

import dev.langchain4j.model.embedding.DimensionAwareEmbeddingModel
import dev.langchain4j.model.ollama.OllamaEmbeddingModel
import it.unibo.utils.Vector

import scala.jdk.CollectionConverters._

object EmbeddingBaseExample {
  def main(args: Array[String]): Unit = {
    val embeddingModel: DimensionAwareEmbeddingModel = OllamaEmbeddingModel.builder()
      .baseUrl("http://localhost:11434")
      .modelName("mxbai-embed-large")
      .logRequests(true)
      .logResponses(true)
      .build()

    val inputTexts = List("Hello", "how", "are", "you")
    val result: List[Vector] = inputTexts
      .map(embeddingModel.embed(_))
      .map(_.content().vector())
      .map(Vector.fromFloatArray)

    println(result.head.getData.length)

    val anotherSentence = Vector.fromFloatArray(
      embeddingModel.embed("Hi").content().vector()
    )

    val distances: List[Double] = result.map(_.distance(anotherSentence))
    val similarities: List[Double] = result.map(_.cosineSimilarity(anotherSentence))

    println(s"Distances: $distances")
    println(s"Similarity: $similarities")
  }
}
