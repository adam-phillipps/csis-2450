require 'set'
require 'asciiart'
require 'byebug'

class MineSweeper
  def initialize
    puts "\n\n\n"
    @input = ''
    until (@input =~ /(stop|exit|quit|cease|decist|terminate|die|^3|kill)+/i)
      play_game
    end
    puts 'bye bye'
  end

  def bomb_in_square?(move)
    move.empty? ? false : (@board[move[0]][move[1].to_i][0] == 'bomb')
  end

  def create_board(size = 20, number_of_bombs)
    @board = Hash[@letters.map { |letter| [letter, Hash.new { ['    ', '--'] }] }]
    number_of_bombs.times { unique_insert(@letters.sample, rand(1..size)) }
  end

  def count_surrounding_bombs(move)
    count = 0
    column_set = (1..@letters.size).to_a
    [-1,0,1].each do |row_num_modifier|
      if (@letters.include? (move[0].ord + row_num_modifier).chr)
        [-1,0,1].each do |col_num_modifier|
          if column_set.include? (move[1].to_i + col_num_modifier)
            count += 1 if @board[(move[0].ord + row_num_modifier).chr][(move[1].to_i + col_num_modifier).to_i][0].eql? 'bomb'
          end
        end
      end
    end
    /(--|!!)/ =~ @board[move[0]][move[1].to_i][1] ? @board[move[0]][move[1].to_i] = ['    ', count.to_s] : @moves += 1
  end

  def difficulty_decipher(value)
    difficulty_map = { 'easy' => 0.9, 'medium' => 0.5, 'hard' => 0.2 }
    if value.kind_of? String
      if difficulty_map.keys.include? value
        ((@letters.size**2) * difficulty_map[value]).floor
      else
        puts 'I\'m not sure what that number is so I\'m going to pick for you'
        ((@letters.size**2) * difficulty_map.values.sample).floor
      end
    else
      (@letters.size**2 - value)
    end
  end

  def dramatic_loss
    lossplosion=AsciiArt.new('./explosion.jpeg')
    puts lossplosion.to_ascii_art
  end

  def dramatic_win
    winsplosion=AsciiArt.new('./victory.jpg')
    puts winsplosion.to_ascii_art
  end

  def gather_game_info
    puts 'Welcome to mine-sweeper. how big of a square do you want the board to be?' "\n\n"
    @input = gets.chomp
    return if (@input =~ /(stop|exit|quit|cease|decist|terminate|die|kill)+/i)
    size = @input.to_i
    if size.kind_of? String
      puts 'I\'m not sure what that number is so I\'m going to pick for you'
      (rand(26) * @difficulty_map.values.sample).floor
    end
    size = (size <= 1) ? 1 : (size - 1)
    @letters = ('a'..(97 + size).chr).to_a
    puts 'ok, pick a level of difficulty.  you can pick easy, medium, hard or a number of bombs.' +  "\n\n"
    @input = gets.chomp
    return if (@input =~ /(stop|exit|quit|cease|decist|terminate|die|kill)+/i)
    spaces = difficulty_decipher(@input)
    [@letters.size, spaces]
  end
  
  def mark(move)
    @board[move[0]][move[1]][1] == 'bomb' ? @board[move[0]][move[1]][1] = '!!' : @board[move[0]][move[1]] = ['    ', '!!']
  end

  def play_game
    bomb = false
    game_info = gather_game_info
    return if game_info.nil?
    @bombs, @moves = (@letters.size**2 - game_info[1]), game_info[1] + 1
    create_board(game_info[0], @bombs)
    until @moves == 0
      show_board 1
      puts 'pick a square.  if you want to mark it, say "mark" or put ! in front of your move.'
      @input = gets.chomp
      return if (@input =~ /(stop|exit|quit|cease|decist|terminate|die|^3|kill)+/i)
      # /(?<marked>(mark|!)?)(?<row>[a-z]{1,2})(?<col>\d+)(?<continue>(stop|exit|quit|cease|decist|terminate|die|^3|kill))/i =~ ok
      /(?<marked>(mark|!)?)(?<row>[a-z]{1,2})(?<col>\d+)/ =~ @input
      unless marked.empty?
        mark [row,col.to_i]
      else
        break if (bomb_in_square? [row,col.to_i]) || ((@moves -= 1) == -1)
        count_surrounding_bombs [row,col.to_i]
      end
    end
    (@moves == 0) ? dramatic_win : dramatic_loss
    show_board 0
  end

  def show_board(version)
    puts "there are #{@bombs} bombs and you have #{@moves - 1} moves left!"
    print '   '
    (1..@letters.size).each { |num| print "#{num}".center(9) }
    puts
    @board.map do |row|
      print "#{row[0]}  "
      (1..@letters.size).each do |num|
        print "| #{@board[row[0]][num][version].center(3)} |".center(9)
      end
      puts ''
    end
    puts "\n\n\n"
  end

  def unique_insert(row, column)
    until @board[row][column][0] == '    '
      row, column = @letters.sample, rand(@letters.size)
    end
    @board[row][column] = ['bomb', '--']
  end
end

MineSweeper.new
