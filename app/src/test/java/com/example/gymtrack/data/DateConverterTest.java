package com.example.gymtrack.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.Date;

public class DateConverterTest {

    private static final long TIMESTAMP_PRUEBA =
            1_710_000_000_000L;

    @Test
    public void dateToTimestamp_convierteFechaCorrectamente() {
        Date fecha =
                new Date(TIMESTAMP_PRUEBA);

        Long resultado =
                DateConverter.dateToTimestamp(fecha);

        assertEquals(
                Long.valueOf(TIMESTAMP_PRUEBA),
                resultado
        );
    }

    @Test
    public void fromTimestamp_convierteTimestampCorrectamente() {
        Date resultado =
                DateConverter.fromTimestamp(
                        TIMESTAMP_PRUEBA
                );

        assertEquals(
                new Date(TIMESTAMP_PRUEBA),
                resultado
        );
    }

    @Test
    public void dateToTimestamp_conFechaNula_devuelveNulo() {
        Long resultado =
                DateConverter.dateToTimestamp(null);

        assertNull(resultado);
    }

    @Test
    public void fromTimestamp_conTimestampNulo_devuelveNulo() {
        Date resultado =
                DateConverter.fromTimestamp(null);

        assertNull(resultado);
    }
}