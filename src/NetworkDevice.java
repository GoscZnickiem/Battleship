public interface NetworkDevice
{

	public void connect();

	public boolean connected();

	public void sendPackage(GamePackage pkg);

	public GamePackage receivePackage();

}
