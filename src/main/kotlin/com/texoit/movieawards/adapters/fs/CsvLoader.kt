package com.texoit.movieawards.adapters.fs

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import io.micronaut.core.io.ResourceLoader
import jakarta.inject.Singleton
import java.io.InputStreamReader

@Singleton
class CsvLoader(private val loader: ResourceLoader): DataLoader {
    override fun load(file: String): CSVReader = CSVReaderBuilder(
        InputStreamReader(loader.getResourceAsStream(file).orElseThrow()))
        .withCSVParser(CSVParserBuilder().withSeparator(';').build())
        .withSkipLines(1)
        .build()
}