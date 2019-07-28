package Finance;

public interface IBaseRate {
	default double getBaseRate() {
		return 0.1;
	}
}
