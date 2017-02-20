library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_DMux4Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_DMux4Way is

component DMux4Way is
	port ( 
			a:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(1 downto 0);
			q0:  out STD_LOGIC;
			q1:  out STD_LOGIC;
			q2:  out STD_LOGIC;
			q3:  out STD_LOGIC);
end component;

	signal inA, outQ0, outQ1, outQ2, outQ3 : STD_LOGIC;
	signal inSel : STD_LOGIC_VECTOR(1 downto 0);

begin

	mapping: DMux4Way port map(inA, inSel, outQ0, outQ1, outQ2, outQ3);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);

      -- Teste: 1
      inA <= '1'; inSel<= "00";
      wait for 200 ps;
      assert(outQ0='1' and outQ1='0' and outQ2='0' and outQ3='0')  report "Falha em teste: 1" severity error;

      -- Teste: 2
      inA <= '1'; inSel<= "01";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='1' and outQ2='0' and outQ3='0')  report "Falha em teste: 2" severity error;

      -- Teste: 3
      inA <= '1'; inSel<= "10";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='1' and outQ3='0')  report "Falha em teste: 3" severity error;

      -- Teste: 4
      inA <= '1'; inSel<= "11";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='1')  report "Falha em teste: 4" severity error;

      -- Teste: 5
      inA <= '0'; inSel<= "00";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0')  report "Falha em teste: 5" severity error;

      -- Teste: 6
      inA <= '0'; inSel<= "01";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0')  report "Falha em teste: 6" severity error;

      -- Teste: 7
      inA <= '0'; inSel<= "10";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0')  report "Falha em teste: 7" severity error;

      -- Teste: 8
      inA <= '0'; inSel<= "11";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0')  report "Falha em teste: 8" severity error;


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;