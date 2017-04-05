library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use IEEE.std_logic_unsigned.all;
use work.all;

entity TesteSDRAM is

    PORT(
        -- Sistema
        CLK_50  : IN  STD_LOGIC; -- 50 Mhz
  
        -- I/Os digital
        LED : out std_logic_vector(7 downto 0) := (others => '0');
        KEY : in  std_logic_vector(1 downto 0);

        -- SDRAM
        DRAM_ADDR   : out   std_logic_vector(12 downto 0);
        DRAM_BA     : out   std_logic_vector(1  downto 0);
        DRAM_CAS_N  : out   std_logic;  
        DRAM_CKE    : out   std_logic;
        DRAM_CLK    : out   std_logic;
        DRAM_CS_N   : out   std_logic;
        DRAM_DQ     : inout std_logic_vector(15 downto 0);
        DRAM_DQM    : out   std_logic_vector(01 downto 0);
        DRAM_RAS_N  : out   std_logic;
        DRAM_WE_N   : out   std_logic
    );
end entity;

ARCHITECTURE logic OF TesteSDRAM IS

    component interfaceSDRAM is
        port (
        CLK_50  : IN  STD_LOGIC; -- 50 Mhz
  
        -- I/Os digital
        LED : out std_logic_vector(7 downto 0) := (others => '0');
        KEY : in  std_logic_vector(1 downto 0);

        -- SDRAM
        DRAM_ADDR   : out   std_logic_vector(12 downto 0);
        DRAM_BA     : out   std_logic_vector(1  downto 0);
        DRAM_CAS_N  : out   std_logic;  
        DRAM_CKE    : out   std_logic;
        DRAM_CLK    : out   std_logic;
        DRAM_CS_N   : out   std_logic;
        DRAM_DQ     : inout std_logic_vector(15 downto 0);
        DRAM_DQM    : out   std_logic_vector(01 downto 0);
        DRAM_RAS_N  : out   std_logic;
        DRAM_WE_N   : out   std_logic
    );
	end component;

    signal RST                      : std_logic;    	 
  

    
begin

    u0 : component interfaceSDRAM
        port map (
				CLK_50 						 => CLK_50,
				LED 								=> LED,
				KEY							 => KEY,
            DRAM_ADDR                 => DRAM_ADDR,
            DRAM_BA                   => DRAM_BA,
            DRAM_CAS_N                => DRAM_CAS_N,
            DRAM_CKE                  => DRAM_CKE,
            DRAM_CS_N                 => DRAM_CS_N,
            DRAM_DQ                   => DRAM_DQ,
            DRAM_DQM                  => DRAM_DQM,
            DRAM_RAS_N                => DRAM_RAS_N,
            DRAM_WE_N                 => DRAM_WE_N
			);

end;




