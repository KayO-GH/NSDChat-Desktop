package com.finalyear.networkservicediscovery.ui;

/**
 * @author Vanessa
 */

import com.finalyear.networkservicediscovery.pojos.ListServiceDescription;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Vector;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static com.finalyear.networkservicediscovery.Main.SOCKET_SERVICE;

/**
 * JList basic tutorial and example
 *
 * @author wwww.codejava.net
 */
public class DiscoveryScreen extends JFrame {

    //Vector to store the list of services identified
    static Vector<ListServiceDescription> descriptors;
    static Vector<String> listData;
    //    static List<listServiceDescription> descriptors;
    private static String serviceName = "KayO";
    //SocketService SOCKET_SERVICE;

    public static void setServiceName(String serviceName) {
        DiscoveryScreen.serviceName = serviceName;
    }

    public static final String SERVICE_TYPE = "_NsdChat._tcp.local.";

    static DefaultListModel<String> listModel = new DefaultListModel<>();

    /*public void setSocketService(SocketService SOCKET_SERVICE) {
        this.SOCKET_SERVICE = SOCKET_SERVICE;
    }*/


    private static class SampleListener implements ServiceListener {

        @Override
        public void serviceAdded(ServiceEvent event) {

            if (!(event.getInfo().getName().equals(serviceName))) {
                System.out.println("Service added: " + event.getInfo());
            }
//            System.out.println(event.getInfo().getName());

//            //if it is not empty then clear old values
//            if(serviceIdentified != null){
//                serviceIdentified.clearAll();
//            }
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {

            System.out.println("Service removed: " + event.getInfo());
            ListServiceDescription serviceRemoval = new ListServiceDescription();
            serviceRemoval.setInstanceName(event.getInfo().getName());
            serviceRemoval.setAddress(event.getInfo().getAddress());
            serviceRemoval.setPort(event.getInfo().getPort());

            if (descriptors.size() > 0) {
                System.out.println("======= Users on Network =======");

                //display all the available services
                for (ListServiceDescription descriptor : descriptors) {

                    if (descriptor.getInstanceName().equals(serviceRemoval.getInstanceName())) {
                        int pos = descriptors.indexOf(descriptor);
                        System.out.println("Service at " + String.valueOf(pos) + " Removed");
                        if (pos > -1) {
                            descriptors.removeElementAt(pos);
                        }
                    }

                    System.out.println(descriptor.toString());
                }
            } else {
                System.out.println("\n---NO Users FOUND---");
            }

//            boolean removePosition = descriptors.contains(serviceRemoval);
        }

        @Override
        public void serviceResolved(ServiceEvent event) {

            System.out.println("Service resolved: " + event.getInfo());
//            descriptors.add(serviceIdentified);

            //a new instance of the listServiceDescription to keep the service that is identified
            ListServiceDescription serviceIdentified;

            serviceIdentified = new ListServiceDescription(event.getInfo().getName(), event.getInfo().getPort(), event.getInfo().getAddress());

            System.out.println(serviceIdentified.getAddress() + " " + serviceIdentified.getInstanceName() + " " + serviceIdentified.getPort());

            //get a list all the services on the network and add them to the vector list of services
            if (!(serviceIdentified.getInstanceName().equals(serviceName))) {
                descriptors.add(serviceIdentified);
                listData.add(serviceIdentified.getInstanceName());
            }

            if (descriptors.size() > 0) {
                System.out.println("======= Users on Network =======");

                //listModel = new DefaultListModel<>();
                //display all the available services

                countryList.setListData(listData);
                for (ListServiceDescription descriptor : descriptors) {
                    System.out.println(descriptor.toString());
                    listModel.addElement(descriptor.toString());

                }
            } else {
                System.out.println("\n---NO Users FOUND---");
            }

        }
    }

    private static JList<String> countryList;

    public DiscoveryScreen(String identity) {
        setServiceName(identity);
        //create the model and add elements

        //create the list
        countryList = new JList<>(listModel);
        countryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final List<String> selectedValuesList = countryList.getSelectedValuesList();
                    System.out.println(selectedValuesList);

                    //Get IP and port of resolved service
                    if (descriptors.size() > 0) {

                        //loop through all the available services
                        for (ListServiceDescription descriptor : descriptors) {

                            if (descriptor.getInstanceName().equals(selectedValuesList.get(0))) {

                                int port = descriptor.getPort();
                                InetAddress ip = descriptor.getAddress();
                                String ip_real = ip.getHostAddress();
                                //close this screen (for now)
                                DiscoveryScreen.this.dispose();
                                //open ChatUIScreen

                                //do already connected check over here
                                //TODO fix the possible malfunction of the alreadyConnected fxn
                                if (!alreadyConnected(ip)) {
                                    ChatUIScreen uiScreen = new ChatUIScreen(false);
                                    SOCKET_SERVICE.setServerUIActivity(uiScreen);
                                    //uiScreen.setService(SOCKET_SERVICE);
                                    System.out.println("DiscoveryScreen: External call to ChatUIScreen called here");
                                    System.out.println("Connecting to IP: " + ip_real + " at PORT: " + port);
                                    uiScreen.main(serviceName, ip, port);
                                    System.out.println("DiscoveryScreen: External call to ChatUIScreen.Main called here");
                                } else {
                                    //TODO logic to continue chat as server
                                    ChatUIScreen uiScreen = new ChatUIScreen(true);
                                    SOCKET_SERVICE.setServerUIActivity(uiScreen);
                                    //uiScreen.setService(SOCKET_SERVICE);
                                    System.out.println("DiscoveryScreen: External call to ChatUIScreen called here");
                                    uiScreen.setServer(true);
                                    uiScreen.main(serviceName, ip, port);
                                    System.out.println("DiscoveryScreen: External call to ChatUIScreen.Main called here");
                                }
                                //break out of for loop
                                break;
                            }

                            //System.out.println(descriptor.toString());


                        }
                    } else {
                        System.out.println("\n---NO Users FOUND---");
                    }
                }
            }
        });

        add(new JScrollPane(countryList));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JList Example");
        this.setSize(200, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        main(identity);

    }

    private boolean alreadyConnected(InetAddress selectedIP) {
        if (SOCKET_SERVICE.getIpSet().contains(selectedIP))
            System.out.println("already Connected: " + selectedIP.toString());
        else
            System.out.println("new Connection: " + selectedIP.toString());
        return SOCKET_SERVICE.getIpSet().contains(selectedIP);
    }

    public void main(String serviceName) {

        descriptors = new Vector<>();
//            descriptors = new ArrayList<>();
        listData = new Vector<>();

        registerService(serviceName);
        discoverServices();

        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DiscoveryScreen(serviceName);
            }
        });*/

    }

    public static void registerService(String serviceName) {

        try {
            ///set mac IP to IPv4
//            setForMac setIPv4 = new setForMac();
//            setIPv4.setToIPv4();

            //creating new socket with random free port
            //ServerSocket serviceSocket = new ServerSocket();
            //serviceSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 0));

            //get ServerSocket from running service and register on the network
            ServerSocket serviceSocket = SOCKET_SERVICE.getServerSocket();

            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create(SERVICE_TYPE, serviceName, serviceSocket.getLocalPort(), "path=index.html");
            System.out.println("Service created");
            jmdns.registerService(serviceInfo);
            System.out.println("Service registered");
            System.out.println("Service-> name:" + serviceInfo.getName() + " IP:" + InetAddress.getLocalHost().getHostAddress() + " Port:" + serviceInfo.getPort());

            // Wait a bit
            Thread.sleep(1000);

            // Unregister all services
            //            jmdns.unregisterAllServices();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException intE) {
            System.out.println(intE.getMessage());
        }

    }

    public static void discoverServices() {
        try {

            //discovering services on network
            System.out.println("discovering services on network......");

            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Add a service listener
            jmdns.addServiceListener(SERVICE_TYPE, new DiscoveryScreen.SampleListener());

            // Wait a bit
            Thread.sleep(1000);

            System.out.println("Working ..... ");

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException intE) {
            System.out.println(intE.getMessage());
        }
    }

}
