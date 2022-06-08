package com.example.wb_week_6_3.utills

import android.graphics.Color
import kotlin.random.Random

fun calculatePi(n: Int): String {
        // найденные цифры сразу же будем записывать в StringBuilder
        val pi = StringBuilder(n)
        val boxes = n * 10 / 3 // размер массива
        val reminders = IntArray(boxes)
        // инициализируем масив двойками
        for (i in 0 until boxes) {
            reminders[i] = 2
        }
        var heldDigits = 0 // счётчик временно недействительных цифр
        for (i in 0 until n) {
            var carriedOver = 0 // перенос на следующий шаг
            var sum = 0
            for (j in boxes - 1 downTo 0) {
                reminders[j] *= 10
                sum = reminders[j] + carriedOver
                val quotient = sum / (j * 2 + 1) // результат деления суммы на знаменатель
                reminders[j] = sum % (j * 2 + 1) // остаток от деления суммы на знаменатель
                carriedOver = quotient * j // j - числитель
            }
            reminders[0] = sum % 10
            var q = sum / 10 // новая цифра числа Пи
            // регулировка недействительных цифр
            when (q) {
                9 -> {
                    heldDigits++
                }
                10 -> {
                    q = 0
                    for (k in 1..heldDigits) {
                        var replaced = pi.substring(i - k, i - k + 1).toInt()
                        if (replaced == 9) {
                            replaced = 0
                        } else {
                            replaced++
                        }
                        pi.deleteCharAt(i - k)
                        pi.insert(i - k, replaced)
                    }
                    heldDigits = 1
                }
                else -> {
                    heldDigits = 1
                }
            }
            pi.append(q) // сохраняем найденную цифру
        }
        if (pi.length >= 2) {
            pi.insert(1, '.') // добавляем в строчку точку после 3
        }
        return pi.toString()
    }

fun getColorForSettingsFragment(): Int {
    val rnd = Random.Default //kotlin.random
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}
