library ieee;
use ieee.std_logic_1164.all;

library vunit_lib;
context vunit_lib.vunit_context;

entity tb_DMux8Way is
  generic (runner_cfg : string);
end entity;

architecture tb of tb_DMux8Way is

component DMux8Way is
	port ( 
			a:   in  STD_LOGIC;
			sel: in  STD_LOGIC_VECTOR(2 downto 0);
			q0:  out STD_LOGIC;
			q1:  out STD_LOGIC;
			q2:  out STD_LOGIC;
			q3:  out STD_LOGIC;
			q4:  out STD_LOGIC;
			q5:  out STD_LOGIC;
			q6:  out STD_LOGIC;
			q7:  out STD_LOGIC);
end component;

	signal inA, outQ0, outQ1, outQ2, outQ3, outQ4, outQ5, outQ6, outQ7 : STD_LOGIC;
	signal inSel : STD_LOGIC_VECTOR(2 downto 0);

begin

	mapping: DMux8Way port map(inA, inSel, outQ0, outQ1, outQ2, outQ3, outQ4, outQ5, outQ6, outQ7);

  main : process
  begin
    test_runner_setup(runner, runner_cfg);


      -- Teste: 1
      inA <= '1'; inSel<= "000";
      wait for 200 ps;
      assert(outQ0='1' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 1" severity error;

      -- Teste: 2
      inA <= '1'; inSel<= "001";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='1' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 2" severity error;

      -- Teste: 3
      inA <= '1'; inSel<= "010";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='1' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 3" severity error;

      -- Teste: 4
      inA <= '1'; inSel<= "011";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='1' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 4" severity error;

      -- Teste: 5
      inA <= '1'; inSel<= "100";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='1' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 5" severity error;

      -- Teste: 6
      inA <= '1'; inSel<= "101";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='1' and outQ6='0' and outQ7='0')  report "Falha em teste: 6" severity error;

      -- Teste: 7
      inA <= '1'; inSel<= "110";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='1' and outQ7='0')  report "Falha em teste: 7" severity error;

      -- Teste: 8
      inA <= '1'; inSel<= "111";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='1')  report "Falha em teste: 8" severity error;

      -- Teste: 9
      inA <= '0'; inSel<= "000";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 9" severity error;

      -- Teste: 10
      inA <= '0'; inSel<= "001";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 10" severity error;

      -- Teste: 11
      inA <= '0'; inSel<= "010";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 11" severity error;

      -- Teste: 12
      inA <= '0'; inSel<= "011";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 12" severity error;

      -- Teste: 13
      inA <= '0'; inSel<= "100";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 13" severity error;

      -- Teste: 14
      inA <= '0'; inSel<= "101";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 14" severity error;

      -- Teste: 15
      inA <= '0'; inSel<= "110";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 15" severity error;

      -- Teste: 16
      inA <= '0'; inSel<= "111";
      wait for 200 ps;
      assert(outQ0='0' and outQ1='0' and outQ2='0' and outQ3='0' and outQ4='0' and outQ5='0' and outQ6='0' and outQ7='0')  report "Falha em teste: 16" severity error;


    test_runner_cleanup(runner); -- Simulacao acaba aqui

  end process;
end architecture;