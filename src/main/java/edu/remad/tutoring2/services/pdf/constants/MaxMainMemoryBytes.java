package edu.remad.tutoring2.services.pdf.constants;

import edu.remad.tutoring2.services.pdf.exceptions.MaxMainMemoryBytesException;

public enum MaxMainMemoryBytes {

	/** Constant for 4MB RAM */
	CONSTANT_4_MB_MEMORY(0, 4000000L),

	/** Constant for 8MB RAM */
	CONSTANT_8_MB_MEMORY(1, 8000000L),

	/** Constant for 16MB RAM */
	CONSTANT_16_MB_MEMORY(2, 16000000L),

	/** Constant for 32MB RAM */
	CONSTANT_32_MB_MEMORY(3, 32000000L),

	/** Constant for 64MB RAM */
	CONSTANT_64_MB_MEMORY(4, 64000000L),

	/** Constant for 128MB RAM */
	CONSTANT_128_MB_MEMORY(5, 128000000L),

	/** Constant for 256MB RAM */
	CONSTANT_256_MB_MEMORY(6, 256000000L),

	/** Constant for 512MB RAM */
	CONSTANT_512_MB_MEMORY(7, 512000000L),

	/** Constant for 1000MB RAM */
	CONSTANT_1000_MB_MEMORY(8, 1000000000L),

	/** Constant for 1000MB RAM */
	CONSTANT_2000_MB_MEMORY(9, 2000000000L),

	/** Constant for 4000MB RAM */
	CONSTANT_4000_MB_MEMORY(10, 4000000000L),

	/** Constant for 8000MB RAM */
	CONSTANT_8000_MB_MEMORY(11, 8000000000L),

	/** Constant for 16000MB RAM */
	CONSTANT_16000_MB_MEMORY(12, 16000000000L);

	private final int ordinalNumber;

	private final long maxMainMemory;

	/**
	 * Constructor of {@link MaxMainMemoryBytes}
	 * 
	 * @param ordinalNumber ordinal number
	 * @param maxMainMemory maximal main memory as number
	 */
	MaxMainMemoryBytes(int ordinalNumber, long maxMainMemory) {
		this.ordinalNumber = ordinalNumber;
		this.maxMainMemory = maxMainMemory;
	}

	/**
	 * @return ordinal number
	 */
	public int getOrdinalNumber() {
		return ordinalNumber;
	}

	/**
	 * @return main memory as number
	 */
	public long getMaxMainMemory() {
		return maxMainMemory;
	}

	/**
	 * Gets {@link MaxMainMemoryBytes} by index
	 * 
	 * @param index index
	 * @return {@link MaxMainMemoryBytes}
	 */
	public static MaxMainMemoryBytes getMaxMainMemoryBytesByIndex(int index) {
		switch (index) {
		case 0:
			return MaxMainMemoryBytes.CONSTANT_4_MB_MEMORY;
		case 1:
			return MaxMainMemoryBytes.CONSTANT_8_MB_MEMORY;
		case 2:
			return MaxMainMemoryBytes.CONSTANT_16_MB_MEMORY;
		case 3:
			return MaxMainMemoryBytes.CONSTANT_32_MB_MEMORY;
		case 4:
			return MaxMainMemoryBytes.CONSTANT_64_MB_MEMORY;
		case 5:
			return MaxMainMemoryBytes.CONSTANT_128_MB_MEMORY;
		case 6:
			return MaxMainMemoryBytes.CONSTANT_256_MB_MEMORY;
		case 7:
			return MaxMainMemoryBytes.CONSTANT_512_MB_MEMORY;
		case 8:
			return MaxMainMemoryBytes.CONSTANT_1000_MB_MEMORY;
		case 9:
			return MaxMainMemoryBytes.CONSTANT_2000_MB_MEMORY;
		case 10:
			return MaxMainMemoryBytes.CONSTANT_4000_MB_MEMORY;
		case 11:
			return MaxMainMemoryBytes.CONSTANT_8000_MB_MEMORY;
		case 12:
			return MaxMainMemoryBytes.CONSTANT_16000_MB_MEMORY;
		default:
			throw new MaxMainMemoryBytesException("MaxMainMemoryBytesException: This index is out of bounds.");
		}
	}

}
