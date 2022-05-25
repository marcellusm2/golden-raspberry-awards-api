package com.texoit.movieawards.adapters.fs

import com.opencsv.CSVReader

interface DataLoader {
    fun load(file: String): CSVReader
}