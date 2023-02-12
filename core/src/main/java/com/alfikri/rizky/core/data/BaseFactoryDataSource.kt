package com.alfikri.rizky.core.data

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version BaseFactoryDataSource, v 0.1 12/26/2022 10:44 AM by Rizky Alfikri Rachmat
 */
abstract class BaseFactoryDataSource <T> {

    abstract fun getSource(source: DataSource): T
}

enum class DataSource {
    LOCALE, REMOTE,
}